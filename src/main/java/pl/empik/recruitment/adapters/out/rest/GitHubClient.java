package pl.empik.recruitment.adapters.out.rest;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import pl.empik.recruitment.adapters.out.rest.dto.UserInfoResponse;
import pl.empik.recruitment.adapters.out.rest.exceptions.ResourceNotFoundException;
import pl.empik.recruitment.adapters.out.rest.exceptions.ServiceNotRespondingException;
import pl.empik.recruitment.configuration.ServicesProperties;
import pl.empik.recruitment.domain.entries.UserInfo;
import pl.empik.recruitment.domain.entries.UserLogin;
import pl.empik.recruitment.domain.ports.outgoing.FindUserInfoByUserLogin;

@Service
public class GitHubClient implements FindUserInfoByUserLogin {

    private static final String USERS = "users";
    private static final String GITHUB_USER_NOT_FOUND_EXCEPTION = "Github User not found!";
    private static final String GITHUB_SERVICE_NOT_RESPONDING_EXCEPTION = "Github Service not responding!";

    private final RestTemplate restTemplate;
    private final ServicesProperties servicesProperties;

    public GitHubClient(RestTemplate restTemplate, ServicesProperties servicesProperties) {
        this.restTemplate = restTemplate;
        this.servicesProperties = servicesProperties;
    }

    private static UserInfo convert(final UserInfoResponse response) {
        return new UserInfo(response.id(), response.login(), response.name(), response.type(),
                response.avatarUrl(), response.createdAt(), response.followers(), response.publicRepos());
    }

    @Override
    public UserInfo execute(final UserLogin userLogin) {
        return convert(getUserInfo(userLogin.value()));
    }

    private UserInfoResponse getUserInfo(final String userLogin) {
        String url = UriComponentsBuilder.newInstance().uri(this.servicesProperties.getGithubURI())
                .pathSegment(USERS, userLogin).build().toUriString();
        try {
            ResponseEntity<UserInfoResponse> response = restTemplate.getForEntity(url, UserInfoResponse.class);
            return response.getBody();
        } catch (RuntimeException exception) {
            if (exception instanceof HttpClientErrorException &&
                    ((HttpClientErrorException) exception).getStatusCode() == HttpStatusCode.valueOf(404)) {
                throw new ResourceNotFoundException(GITHUB_USER_NOT_FOUND_EXCEPTION);
            }
            throw new ServiceNotRespondingException(GITHUB_SERVICE_NOT_RESPONDING_EXCEPTION);
        }
    }
}

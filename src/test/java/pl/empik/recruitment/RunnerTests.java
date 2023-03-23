package pl.empik.recruitment;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import org.springframework.web.util.UriComponentsBuilder;
import pl.empik.recruitment.adapters.in.rest.UsersController;
import pl.empik.recruitment.adapters.in.rest.dto.UserInfoResponse;
import pl.empik.recruitment.adapters.out.rest.exceptions.ResourceNotFoundException;
import pl.empik.recruitment.adapters.out.rest.exceptions.ServiceNotRespondingException;
import pl.empik.recruitment.adapters.out.sql.LoginRegisterRepository;
import pl.empik.recruitment.adapters.out.sql.entries.LoginRegisterEntity;
import pl.empik.recruitment.configuration.ServicesProperties;
import pl.empik.recruitment.handler.GlobalExceptionHandler;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Objects;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withServerError;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class RunnerTests {

    private final static int EXPECTED_PORT = 8080;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UsersController usersController;
    @Autowired
    ServicesProperties servicesProperties;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    LoginRegisterRepository loginRegisterRepository;

    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(this.restTemplate);
        this.mockServer = MockRestServiceServer.createServer(gateway);
    }

    @ParameterizedTest
    @CsvSource({"1, otocat", "2, otocat", "1, johnny333"})
    void getUserByLoginExpectedSuccess(Long index, String user) throws IOException {
        String serviceResponseFile = getFileContent("users/" + user + index + ".json");
        String expectedResultFile = getFileContent("users/" + user + index + "ex.json");

        var expectedResult = mapper.readValue(expectedResultFile, pl.empik.recruitment.adapters.in.rest.dto.UserInfoResponse.class);

        this.mockServer.expect(requestTo(UriComponentsBuilder.newInstance().uri(this.servicesProperties.getGithubURI()).pathSegment("users", user).build().toUri())).andRespond(withSuccess(serviceResponseFile, MediaType.APPLICATION_JSON));

        UserInfoResponse result = usersController.getUserInfo(user);

        LoginRegisterEntity register = loginRegisterRepository.findByLogin(user).orElseThrow();
        assert Objects.equals(result.id(), expectedResult.id());
        assert Objects.equals(result.login(), expectedResult.login());
        assert Objects.equals(result.name(), expectedResult.name());
        assert Objects.equals(result.type(), expectedResult.type());
        assert Objects.equals(result.avatarUrl(), expectedResult.avatarUrl());
        assert Objects.equals(result.createdAt(), expectedResult.createdAt());
        assert expectedResult.calculation().compareTo(result.calculation()) == 0;
        assert register.getRegisterCount().equals(index);
    }

    @ParameterizedTest
    @ValueSource(strings = {"otocat"})
    void getUserByLoginExpectedHttpResponseResourceNotFoundError(String user) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + EXPECTED_PORT + "/users/" + user)).build();
        this.mockServer.expect(requestTo(UriComponentsBuilder.newInstance().uri(this.servicesProperties.getGithubURI()).pathSegment("users", user).build().toUri()))
                .andRespond(withResourceNotFound());

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GlobalExceptionHandler.Error error = mapper.readValue(response.body(), GlobalExceptionHandler.Error.class);
        assert error.getClassName().equals(ResourceNotFoundException.class.getName());

    }

    @ParameterizedTest
    @ValueSource(strings = {"otocat"})
    void getUserByLoginExpectedHttpResponseServiceNotRespondingError(String user) throws IOException, InterruptedException {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:" + EXPECTED_PORT + "/users/" + user)).build();
        this.mockServer.expect(requestTo(UriComponentsBuilder.newInstance().uri(this.servicesProperties.getGithubURI()).pathSegment("users", user).build().toUri())).andRespond(withServerError());

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        GlobalExceptionHandler.Error error = mapper.readValue(response.body(), GlobalExceptionHandler.Error.class);
        assert error.getClassName().equals(ServiceNotRespondingException.class.getName());
    }

    private static String getFileContent(String fileName) throws IOException {
        ClassLoader classLoader = RunnerTests.class.getClassLoader();
        return Files.readString(new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile()).toPath());
    }

}

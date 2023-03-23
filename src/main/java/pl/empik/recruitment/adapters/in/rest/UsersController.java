package pl.empik.recruitment.adapters.in.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.empik.recruitment.adapters.in.rest.dto.UserInfoResponse;
import pl.empik.recruitment.domain.entries.UserInfo;
import pl.empik.recruitment.domain.entries.UserLogin;
import pl.empik.recruitment.domain.ports.incoming.GetUserInfo;

import static pl.empik.recruitment.adapters.Constants.PARAM_LOGIN;
import static pl.empik.recruitment.adapters.Constants.URI_USERS;
import static pl.empik.recruitment.adapters.Constants.URI_USERS_LOGIN;

@RestController
@RequestMapping(URI_USERS)
public class UsersController {

    private final GetUserInfo getUserInfo;

    public UsersController(final GetUserInfo getUserInfo) {
        this.getUserInfo = getUserInfo;
    }

    @GetMapping({URI_USERS_LOGIN})
    public UserInfoResponse getUserInfo(@PathVariable(value = PARAM_LOGIN) String login) {
        UserLogin userLogin = new UserLogin(login);
        UserInfo userInfo = getUserInfo.execute(userLogin);
        return convert(userInfo);
    }

    private static UserInfoResponse convert(UserInfo userInfo) {
        return new UserInfoResponse(
                userInfo.getId(),
                userInfo.getLogin(),
                userInfo.getName(),
                userInfo.getType(),
                userInfo.getAvatarUrl(),
                userInfo.getCreatedAt(),
                userInfo.getCalculation());
    }

}


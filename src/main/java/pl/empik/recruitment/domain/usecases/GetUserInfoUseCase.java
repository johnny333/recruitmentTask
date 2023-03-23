package pl.empik.recruitment.domain.usecases;

import org.springframework.stereotype.Component;
import pl.empik.recruitment.domain.entries.UserInfo;
import pl.empik.recruitment.domain.entries.UserLogin;
import pl.empik.recruitment.domain.ports.incoming.GetUserInfo;
import pl.empik.recruitment.domain.ports.outgoing.FindUserInfoByUserLogin;
import pl.empik.recruitment.domain.ports.outgoing.RegisterUserCall;

import java.math.BigDecimal;

@Component
public class GetUserInfoUseCase implements GetUserInfo {

    private final FindUserInfoByUserLogin findUserInfoByUserLogin;
    private final RegisterUserCall registerUserCall;

    public GetUserInfoUseCase(final FindUserInfoByUserLogin findUserInfoByUserLogin, final RegisterUserCall registerUserCall) {
        this.findUserInfoByUserLogin = findUserInfoByUserLogin;
        this.registerUserCall = registerUserCall;
    }

    @Override
    public UserInfo execute(UserLogin userLogin) {
        UserInfo execute = findUserInfoByUserLogin.execute(userLogin);
        registerUserCall.execute(userLogin);
        execute.setCalculation(calculate(execute.getFollowers(), execute.getPublicRepos()));
        return execute;
    }

    private BigDecimal calculate(Long followers, Long publicRepos) {
        if (followers == 0) {
            return BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(6.0 / followers * (2 + publicRepos));

    }
}

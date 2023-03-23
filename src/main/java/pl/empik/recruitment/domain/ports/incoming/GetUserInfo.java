package pl.empik.recruitment.domain.ports.incoming;

import pl.empik.recruitment.domain.entries.UserInfo;
import pl.empik.recruitment.domain.entries.UserLogin;

public interface GetUserInfo {
    UserInfo execute(UserLogin userLogin);
}

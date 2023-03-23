package pl.empik.recruitment.domain.ports.outgoing;

import pl.empik.recruitment.domain.entries.UserInfo;
import pl.empik.recruitment.domain.entries.UserLogin;

public interface FindUserInfoByUserLogin {
    UserInfo execute(UserLogin userLogin);
}

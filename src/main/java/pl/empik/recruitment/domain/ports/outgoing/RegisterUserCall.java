package pl.empik.recruitment.domain.ports.outgoing;

import pl.empik.recruitment.domain.entries.UserLogin;

public interface RegisterUserCall {
    void execute(UserLogin userLogin);
}

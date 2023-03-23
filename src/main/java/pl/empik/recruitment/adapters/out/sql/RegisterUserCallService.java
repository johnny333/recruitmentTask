package pl.empik.recruitment.adapters.out.sql;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.empik.recruitment.adapters.out.sql.entries.LoginRegisterEntity;
import pl.empik.recruitment.domain.entries.UserLogin;
import pl.empik.recruitment.domain.ports.outgoing.RegisterUserCall;

@Service
public class RegisterUserCallService implements RegisterUserCall {
    private final LoginRegisterRepository loginRegisterRepository;

    public RegisterUserCallService(final LoginRegisterRepository loginRegisterRepository) {
        this.loginRegisterRepository = loginRegisterRepository;
    }

    @Transactional
    public void execute(UserLogin userLogin) {
        LoginRegisterEntity oneByLogin = loginRegisterRepository.findByLogin(userLogin.value())
                .orElseGet(() -> new LoginRegisterEntity(userLogin.value()));
        oneByLogin.setRegisterCount(oneByLogin.getRegisterCount() + 1);
        loginRegisterRepository.save(oneByLogin);
    }
}

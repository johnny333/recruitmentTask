package pl.empik.recruitment.adapters.out.sql;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.empik.recruitment.adapters.out.sql.entries.LoginRegisterEntity;

import java.util.Optional;

@Repository
public interface LoginRegisterRepository extends JpaRepository<LoginRegisterEntity, Long> {
    Optional<LoginRegisterEntity> findByLogin(String value);
}

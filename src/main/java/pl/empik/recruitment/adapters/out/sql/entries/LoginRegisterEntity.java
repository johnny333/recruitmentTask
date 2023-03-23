package pl.empik.recruitment.adapters.out.sql.entries;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = EntryDict.LOGIN_REGISTER._TABLE)
@SequenceGenerator(name = EntryDict.LOGIN_REGISTER._SEQ, allocationSize = 1)
public class LoginRegisterEntity implements Serializable {
    @Id
    @Column(name = EntryDict.LOGIN_REGISTER.ID)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = EntryDict.LOGIN_REGISTER._SEQ)
    private Long id;
    @Column(name = EntryDict.LOGIN_REGISTER.LOGIN)
    private String login;

    @Column(name = EntryDict.LOGIN_REGISTER.REQUEST_COUNT)
    private Long registerCount = 0l;

    public LoginRegisterEntity() {
    }

    public LoginRegisterEntity(String login) {
        this.login = login;
    }

    public Long getRegisterCount() {
        return registerCount;
    }

    public void setRegisterCount(Long registerCount) {
        this.registerCount = registerCount;
    }
}

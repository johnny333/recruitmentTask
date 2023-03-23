package pl.empik.recruitment.domain.entries;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserInfo {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private LocalDateTime createdAt;
    private Long followers;
    private Long publicRepos;

    private BigDecimal calculation;


    public UserInfo(Long id, String login, String name, String type, String avatarUrl, LocalDateTime createdAt, Long followers, Long publicRepos) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.type = type;
        this.avatarUrl = avatarUrl;
        this.createdAt = createdAt;
        this.followers = followers;
        this.publicRepos = publicRepos;
        this.calculation = BigDecimal.ZERO;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Long getFollowers() {
        return followers;
    }

    public Long getPublicRepos() {
        return publicRepos;
    }

    public BigDecimal getCalculation() {
        return calculation;
    }

    public void setCalculation(BigDecimal calculation) {
        this.calculation = calculation;
    }
}

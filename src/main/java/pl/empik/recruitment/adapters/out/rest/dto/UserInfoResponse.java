package pl.empik.recruitment.adapters.out.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserInfoResponse(
        Long id, String login,
        String name, String type,
        @JsonProperty("avatar_url") String avatarUrl, @JsonProperty("created_at") LocalDateTime createdAt,
        Long followers,
        @JsonProperty("public_repos") Long publicRepos


) implements Serializable {
}
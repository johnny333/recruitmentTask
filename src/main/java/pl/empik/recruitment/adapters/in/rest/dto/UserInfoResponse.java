package pl.empik.recruitment.adapters.in.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record UserInfoResponse(Long id, String login,
                               String name, String type,
                               String avatarUrl, LocalDateTime createdAt,
                               BigDecimal calculation) implements Serializable {
}
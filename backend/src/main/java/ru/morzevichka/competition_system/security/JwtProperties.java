package ru.morzevichka.competition_system.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Data
public class JwtProperties {
    private String secretKey;
    private Long accessExpiration;
    private Long refreshExpiration;
}

package com.challenge.infra.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
@Getter
@RequiredArgsConstructor
public class RateLimitConfig {

    @Value("${STATUS_MAX_NOTIFICATIONS}")
    private String statusMaxRate;

    @Value("${NEWS_MAX_NOTIFICATIONS}")
    private String newsMaxRate;

    @Value("${MARKETING_MAX_NOTIFICATIONS}")
    private String marketingMaxRate;
}

package com.challenge.infra.adapter;

import com.challenge.aplication.port.RateRulesPort;
import com.challenge.infra.config.RateLimitConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class RateRulesAdapter implements RateRulesPort {
    private final Map<String, String> typeLimits;

    @Autowired
    public RateRulesAdapter(RateLimitConfig rateLimitConfig) {
        this.typeLimits = new HashMap<>();
        typeLimits.put("status", rateLimitConfig.getStatusMaxRate());
        typeLimits.put("news", rateLimitConfig.getNewsMaxRate());
        typeLimits.put("marketing", rateLimitConfig.getMarketingMaxRate());
    }

    @Override
    public String getRateRulePerType(String type) {
        return this.typeLimits.get(type);
    }
}
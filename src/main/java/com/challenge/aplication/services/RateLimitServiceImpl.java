package com.challenge.aplication.services;

import com.challenge.aplication.port.RateRulesPort;
import com.challenge.domain.RateLimit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class RateLimitServiceImpl implements RateLimitService {

    private final RateRulesPort rateLimitConfig;
    private final Map<String, Map<String, AtomicInteger>> userNotifications = new ConcurrentHashMap<>();
    private Instant lastResetTimestamp = Instant.now();

    @Override
    public boolean withinTimeLimit(String userId, String notificationType) {
        Instant currentTimestamp = Instant.now();
        RateLimit rateLimit = getRateLimit(notificationType);

        Map<String, AtomicInteger> notificationCounts = userNotifications
                .computeIfAbsent(userId, k -> new ConcurrentHashMap<>());

        long timeLimitMillis = convertToMilliseconds(rateLimit.getValue(), rateLimit.getUnit());

        if (currentTimestamp.minusMillis(timeLimitMillis).isAfter(lastResetTimestamp)) {
            notificationCounts.get(notificationType).set(0);
            lastResetTimestamp = currentTimestamp;
        }

        if (notificationCounts.computeIfAbsent(notificationType,
                k -> new AtomicInteger(0)).get() >= rateLimit.getValue()) {
            return false;
        }

        int newCount = notificationCounts.get(notificationType).incrementAndGet();

        return newCount <= rateLimit.getValue();
    }

    private RateLimit getRateLimit(String notificationType) {
        String rate = rateLimitConfig.getRateRulePerType(notificationType);
        if (rate == null) {
            throw new NoSuchElementException();
        }
        return new RateLimit(rate);
    }

    private long convertToMilliseconds(int value, String unit) {
        return switch (unit) {
            case "m" -> value * 60L * 1000L;
            case "h" -> value * 60L * 60L * 1000L;
            case "d" -> value * 24L * 60L * 60L * 1000L;
            default -> throw new IllegalArgumentException("Unidad de tiempo no valida: " + unit);
        };
    }
}

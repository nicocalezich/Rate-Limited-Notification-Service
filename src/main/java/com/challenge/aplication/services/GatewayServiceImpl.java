package com.challenge.aplication.services;

import com.challenge.aplication.exception.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GatewayServiceImpl implements GatewayService {

    private final RateLimitService rateLimitService;

    @Override
    public void sendEmailNotification(String type, String userId, String message) throws RateLimitExceededException {
        if (!rateLimitService.withinTimeLimit(userId, type)) {
            throw new RateLimitExceededException();
        }
        System.out.println("Sending email to user " + userId + ": " + message);
    }

}

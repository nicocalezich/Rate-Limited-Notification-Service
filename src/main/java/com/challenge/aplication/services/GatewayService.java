package com.challenge.aplication.services;

import com.challenge.aplication.exception.RateLimitExceededException;

public interface GatewayService {
    void sendEmailNotification(String type, String userId, String message) throws RateLimitExceededException;
}
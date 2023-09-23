package com.challenge.aplication.services;

public interface RateLimitService {
    boolean withinTimeLimit(String userId, String type);
}
package com.challenge.infra.controller;

import org.springframework.http.ResponseEntity;

public interface NotificationController {
    ResponseEntity<String> sendNotification(String type, String userId, String message);
}

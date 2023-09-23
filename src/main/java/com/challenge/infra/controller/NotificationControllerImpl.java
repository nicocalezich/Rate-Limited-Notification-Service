package com.challenge.infra.controller;

import com.challenge.aplication.exception.RateLimitExceededException;
import com.challenge.aplication.services.GatewayService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationControllerImpl implements NotificationController {

    private final GatewayService gatewayService;

    @PostMapping("/send")
    public ResponseEntity<String> sendNotification(
            @RequestParam String type,
            @RequestParam String userId,
            @RequestBody String message) {
        try {
            gatewayService.sendEmailNotification(type, userId, message);
            return ResponseEntity.ok("Notification sent successfully");
        } catch (RateLimitExceededException e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Rate limit exceeded for this notification type");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An internal server error occurred " + e.getMessage());
        }

    }
}
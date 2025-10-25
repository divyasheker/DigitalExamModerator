package com.exam.digital.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpMethod; // Import HttpMethod

@RestController
@RequestMapping("/api/ai")
@CrossOrigin(origins = "http://localhost:3000") // Allow frontend
public class AIController {

    private final RestTemplate restTemplate = new RestTemplate();
    // Make URL configurable if possible, but hardcoding for now
    private final String AI_SERVER_URL = "http://localhost:5001";

    @PostMapping("/start")
    public ResponseEntity<String> startMonitoring() {
        String url = AI_SERVER_URL + "/start-monitoring";
        System.out.println("Spring Boot: Sending request to " + url); // Add log
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println("Spring Boot: Response from AI Server (Start): " + response.getStatusCode()); // Add log
            return ResponseEntity.ok("AI Monitoring Started");
        } catch (Exception e) {
            System.err.println("Spring Boot: Failed to start AI Monitoring: " + e.getMessage()); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to start AI Monitoring: " + e.getMessage());
        }
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopMonitoring() {
        String url = AI_SERVER_URL + "/stop-monitoring";
        System.out.println("Spring Boot: Sending request to " + url); // Add log
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println("Spring Boot: Response from AI Server (Stop): " + response.getStatusCode()); // Add log
            return ResponseEntity.ok("AI Monitoring Stopped");
        } catch (Exception e) {
            System.err.println("Spring Boot: Failed to stop AI Monitoring: " + e.getMessage()); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to stop AI Monitoring: " + e.getMessage());
        }
    }

    // *** NEW: Endpoint to Get Status from AI Server ***
    @GetMapping("/status")
    public ResponseEntity<String> getMonitoringStatus() {
        String url = AI_SERVER_URL + "/get-status";
        System.out.println("Spring Boot: Sending request to " + url); // Add log
        try {
            // Use exchange for GET request expecting a JSON body (returned as String here)
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, null, String.class);
            System.out.println("Spring Boot: Response from AI Server (Status): " + response.getStatusCode()); // Add log
            // Forward the JSON string response from the AI server
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            System.err.println("Spring Boot: Failed to get AI status: " + e.getMessage()); // Log error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\": \"Failed to get AI status: " + e.getMessage() + "\"}"); // Return error as JSON string
        }
    }
}

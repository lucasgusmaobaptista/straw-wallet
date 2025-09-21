package me.lucasgusmao.straw_wallet_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"test", "status"})
public class TestController {

    // Simple endpoint to verify that the application is running
    @GetMapping
    public String testEndpoint() {
        return "Test successful! Application is running.";
    }

    @GetMapping("/secure")
    public String securityTest() {
        return "Security test successful! You are authenticated.";
    }
}

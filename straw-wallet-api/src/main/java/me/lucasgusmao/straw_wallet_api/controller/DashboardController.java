package me.lucasgusmao.straw_wallet_api.controller;

import lombok.RequiredArgsConstructor;
import me.lucasgusmao.straw_wallet_api.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService service;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        Map<String, Object> dashboardData = service.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }
}

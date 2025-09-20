package org.example.demo_library.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping
    public Map<String, String> test() {
        return Map.of("message", "後端API正常運行", "status", "OK");
    }
    
    @GetMapping("/inventory")
    public Map<String, String> testInventory() {
        return Map.of("message", "庫存API端點正常", "status", "OK");
    }
}

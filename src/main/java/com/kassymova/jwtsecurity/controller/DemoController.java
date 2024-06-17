package com.kassymova.jwtsecurity.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
public class DemoController {

    @GetMapping("/profile")
    public ResponseEntity<String> demo() {
        return ResponseEntity.ok("Hello World! from security controller");
    }
}

package com.example.farm.login;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/secure")
public class SecureController {

    @GetMapping("/user/hello")
    public String userHello() {
        return "Hello User 👋 You have access to USER APIs!";
    }

    @GetMapping("/admin/hello")
    public String adminHello() {
        return "Hello Admin 👑 You have access to ADMIN APIs!";
    }
}

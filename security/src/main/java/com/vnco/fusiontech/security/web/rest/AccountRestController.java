package com.vnco.fusiontech.security.web.rest;

import com.vnco.fusiontech.common.web.request.RegisterRequest;
import com.vnco.fusiontech.security.service.RegisterService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/security")
public class AccountRestController {

    final
    RegisterService registerService;

    public AccountRestController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @GetMapping
    @ResponseBody
    public String security() {
        return "hello world";
    }

    @PostMapping("/register")
    public UUID register(@RequestBody RegisterRequest registerRequest) {
        return registerService.register(registerRequest);
    }
}

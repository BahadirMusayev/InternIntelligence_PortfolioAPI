package com.example.internintelligenceportfolioapi.controller.auth;

import com.example.internintelligenceportfolioapi.model.auth.AuthenticationDto;
import com.example.internintelligenceportfolioapi.model.input.UserLoginDtoInput;
import com.example.internintelligenceportfolioapi.model.input.UserRegistrationDtoInput;
import com.example.internintelligenceportfolioapi.service.auth.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/user")
public class UserAuthController {

    private final UserAuthService userAuthService;

    @PostMapping("/sign/up")
    public void signUp(@RequestBody UserRegistrationDtoInput userRegistrationDto) {
        userAuthService.signUp(userRegistrationDto);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(
            @RequestBody UserLoginDtoInput userLoginDto) {
        return ResponseEntity.ok(userAuthService.login(userLoginDto));
    }
}

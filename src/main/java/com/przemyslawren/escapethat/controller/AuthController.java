package com.przemyslawren.escapethat.controller;

import com.przemyslawren.escapethat.dto.LoginRequestDto;
import com.przemyslawren.escapethat.service.AuthService;
import com.przemyslawren.escapethat.model.CustomUserDetails;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        Authentication authentication = authService.authenticate(loginRequestDto);
        if (authentication == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String role = customUserDetails.getAuthorities().stream().findFirst().get().getAuthority();
        Long customerId = customUserDetails.getId();;
        Map<String, Object> response = new HashMap<>();
        response.put("user", customUserDetails);
        response.put("role", role);
        response.put("customerId", customerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/check-auth")
    public ResponseEntity<?> checkAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok().body("Authenticated");
        }
        return ResponseEntity.status(401).body("Not Authenticated");
    }
}
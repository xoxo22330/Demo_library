package org.example.demo_library.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.demo_library.domain.User;
import org.example.demo_library.domain.dto.LoginRequest;
import org.example.demo_library.domain.dto.RegisterRequest;
import org.example.demo_library.repository.UserRepository;
import org.example.demo_library.security.JwtService;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Map;

@RestController @RequestMapping("/api/auth") @RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    // 註冊帳號
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest r) {
        if (userRepo.existsByPhoneNumber(r.getPhoneNumber()))
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("message","Phone already exists"));
        User u = new User();
        u.setPhoneNumber(r.getPhoneNumber());
        u.setPasswordHash(encoder.encode(r.getPassword())); // BCrypt 內含隨機鹽
        u.setUserName(r.getUserName());
        u.setRegistrationTime(LocalDateTime.now());
        userRepo.save(u);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "userId", u.getUserId(), "phoneNumber", u.getPhoneNumber(), "userName", u.getUserName()
        ));
    }

    // 登入帳號
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest r) {
        User u = userRepo.findByPhoneNumber(r.getPhoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials"));
        if (!encoder.matches(r.getPassword(), u.getPasswordHash()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad credentials");
        u.setLastLoginTime(LocalDateTime.now()); userRepo.save(u);
        
        String token = jwt.generateToken(u.getUserId());
        return ResponseEntity.ok(Map.of(
            "token", token,
            "user", Map.of(
                "userId", u.getUserId(),
                "userName", u.getUserName(),
                "phoneNumber", u.getPhoneNumber()
            )
        ));
    }
}

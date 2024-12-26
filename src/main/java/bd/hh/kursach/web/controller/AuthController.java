package bd.hh.kursach.web.controller;

import bd.hh.kursach.service.AuthService;
import bd.hh.kursach.web.dto.AuthRequest;
import bd.hh.kursach.web.dto.RefreshRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticateAndGenerationTokens(authRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshRequest refreshToken) {
        return ResponseEntity.ok(authService.refreshAccessToken(refreshToken));
    }
}
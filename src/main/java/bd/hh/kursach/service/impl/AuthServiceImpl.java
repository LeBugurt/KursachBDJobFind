package bd.hh.kursach.service.impl;

import bd.hh.kursach.exception.InvalidRefreshTokenException;
import bd.hh.kursach.service.AuthService;
import bd.hh.kursach.util.JwtUtil;
import bd.hh.kursach.web.dto.AuthRequest;
import bd.hh.kursach.web.dto.AuthResponse;
import bd.hh.kursach.web.dto.RefreshRequest;
import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public AuthResponse authenticateAndGenerationTokens(AuthRequest authRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        String accessToken = jwtUtil.generateAccessToken(authRequest.getEmail());
        String refreshToken = jwtUtil.generateRefreshToken(authRequest.getEmail());

        return new AuthResponse(accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshAccessToken(RefreshRequest refreshToken) {
        if (!jwtUtil.validateToken(refreshToken.getRefreshToken())) {
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }
        try {
            String username = jwtUtil.extractUsername(refreshToken.getRefreshToken());
            String accessToken = jwtUtil.generateAccessToken(username);

            return new AuthResponse(accessToken, refreshToken.getRefreshToken());
        } catch (JwtException e) {
            throw new InvalidRefreshTokenException("Invalid refresh token: " + e.getMessage());
        }
    }
}

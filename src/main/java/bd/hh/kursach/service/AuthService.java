package bd.hh.kursach.service;

import bd.hh.kursach.web.dto.AuthRequest;
import bd.hh.kursach.web.dto.AuthResponse;
import bd.hh.kursach.web.dto.RefreshRequest;

public interface AuthService {

    AuthResponse authenticateAndGenerationTokens(AuthRequest authRequest);

    AuthResponse refreshAccessToken(RefreshRequest refreshToken);
}

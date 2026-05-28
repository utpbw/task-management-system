package taskmanagement.controller;

import taskmanagement.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * REST controller for authentication operations.
 *
 * <p>Provides an endpoint for registered users to exchange their Basic Auth
 * credentials for a Bearer token, which can then be used for all subsequent
 * API requests instead of sending credentials on every request.</p>
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenService tokenService;

    /**
     * Constructs the controller with the required token service.
     *
     * @param tokenService service responsible for generating and validating tokens
     */
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Issues a Bearer token for an authenticated user.
     *
     * <p>This endpoint requires Basic HTTP authentication. On success, it generates
     * a time-limited opaque token and returns it to the client. The client should
     * store this token and include it in subsequent requests as:
     * {@code Authorization: Bearer <token>}</p>
     *
     * @param authentication the verified identity injected by Spring Security
     * @return 200 OK with {@code {"token": "<value>"}} on success,
     *         401 UNAUTHORIZED if credentials are missing or invalid
     */
    @PostMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(Authentication authentication) {
        String token = tokenService.generateToken(authentication.getName());
        return ResponseEntity.ok(Map.of("token", token));
    }
}

package taskmanagement.service;

import taskmanagement.model.Token;
import taskmanagement.repository.TokenRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TokenService {

    private final TokenRepository tokenRepository;

    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(String email) {
        String tokenValue = UUID.randomUUID().toString();
        Token token = new Token();
        token.setTokenValue(tokenValue);
        token.setEmail(email);
        token.setExpiresAt(LocalDateTime.now().plusHours(1));
        tokenRepository.save(token);
        return tokenValue;
    }

    public Optional<String> validateToken(String tokenValue) {
        return tokenRepository.findByTokenValue(tokenValue)
                .filter(t -> t.getExpiresAt().isAfter(LocalDateTime.now()))
                .map(Token::getEmail);
    }
}

package taskmanagement.service;

import taskmanagement.dto.RegistrationRequest;
import taskmanagement.model.UserAccount;
import taskmanagement.repository.UserAccountRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserAccountService {

    private final UserAccountRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserAccountService(UserAccountRepository repository,
                               PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccount register(RegistrationRequest request) {
        // Normalise to lowercase so "ALICE@email.com" and "alice@email.com"
        // are treated as the same address during the duplicate check.
        String normalisedEmail = request.getEmail().toLowerCase();

        if (repository.existsByEmail(normalisedEmail)) {
            // Spring MVC catches this exception and sends a 409 CONFLICT response.
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "This email address is already registered");
        }

        // We never store the raw password — BCrypt hashes it with a random salt.
        String hashedPassword = passwordEncoder.encode(request.getPassword());
        return repository.save(new UserAccount(normalisedEmail, hashedPassword));
    }
}

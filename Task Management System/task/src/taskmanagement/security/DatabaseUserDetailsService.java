package taskmanagement.security;

import taskmanagement.model.UserAccount;
import taskmanagement.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserAccountRepository repository;

    public DatabaseUserDetailsService(UserAccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Normalise to lowercase before the lookup so that a client sending
        // "ALICE@email.com" in the Basic Auth header still finds the row
        // that was stored as "alice@email.com" during registration.
        UserAccount account = repository
                .findByEmail(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("No account: " + username));

        // Wrap our entity in Spring Security's User object, which the framework
        // uses to compare the incoming password against the stored BCrypt hash.
        return User.withUsername(account.getEmail())
                .password(account.getPassword())
                .roles("USER")
                .build();
    }
}

package taskmanagement.repository;

import taskmanagement.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    // Spring Data reads the method name and generates the SQL automatically.
    // existsByEmail issues a lightweight "SELECT 1" — no need to fetch the full row.
    boolean existsByEmail(String email);

    // Used at login time to load the user's stored BCrypt hash for verification.
    Optional<UserAccount> findByEmail(String email);
}

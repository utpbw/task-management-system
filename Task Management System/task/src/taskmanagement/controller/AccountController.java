package taskmanagement.controller;

import taskmanagement.dto.RegistrationRequest;
import taskmanagement.service.UserAccountService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final UserAccountService userAccountService;

    public AccountController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    // @Valid is the bridge between the HTTP layer and your validation rules.
    // Before this method body runs, Spring inspects every annotation on
    // RegistrationRequest (@NotBlank, @Email, @Size). If any constraint
    // fails, Spring throws MethodArgumentNotValidException automatically,
    // which GlobalExceptionHandler catches and converts into a 400 response.
    // If all constraints pass, execution continues into the method body.
    @PostMapping("/accounts")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request) {
        userAccountService.register(request);
        return ResponseEntity.ok().build();
    }
}

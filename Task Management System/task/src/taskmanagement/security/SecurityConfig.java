package taskmanagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Tell Spring Security to accept credentials via the
                // "Authorization: Basic <base64>" HTTP header.
                .httpBasic(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        // Registration must be public — users cannot log in
                        // before they have an account, so this endpoint
                        // cannot require authentication.
                        .requestMatchers("/api/accounts").permitAll()
                        .requestMatchers("/error").permitAll()
                        // The test runner shuts the app down via this endpoint.
                        .requestMatchers("/actuator/shutdown").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        // Every other endpoint, including /api/tasks,
                        // requires a valid username and password.
                        // Spring returns 401 automatically — no code needed.
                        .anyRequest().authenticated()
                )
                // Disable CSRF so the test framework can send POST requests freely.
                .csrf(AbstractHttpConfigurer::disable)
                // Stateless means no session cookie is created — each request
                // must carry its own credentials in the Authorization header.
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Allows the H2 console to render inside an iframe in the browser.
                .headers(h -> h.frameOptions(f -> f.sameOrigin()))
                .build();
    }

    // Declaring BCryptPasswordEncoder as a @Bean makes it available for
    // injection into UserAccountService (for hashing) and Spring Security
    // uses it automatically for verifying passwords at login time.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

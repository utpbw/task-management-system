package taskmanagement.security;

import taskmanagement.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

/**
 * Servlet filter that authenticates requests carrying a Bearer token.
 *
 * <p>Runs once per request before Spring Security's standard authentication
 * filters. If the {@code Authorization: Bearer <token>} header is present and
 * the token is valid and unexpired, this filter populates the
 * {@link SecurityContextHolder} with the corresponding user's identity so that
 * downstream filters and controllers treat the request as authenticated.</p>
 *
 * <p>If the header is absent, malformed, or the token is invalid, this filter
 * does nothing — it does not reject the request itself. Rejection happens later
 * in the filter chain when Spring Security finds no authentication in the context.</p>
 */
@Component
public class BearerTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    /**
     * @param tokenService used to validate the token and resolve the owner's email
     */
    public BearerTokenFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    /**
     * Extracts the Bearer token from the Authorization header, validates it,
     * and sets the authentication in the security context if valid.
     *
     * @param request  the incoming HTTP request
     * @param response the HTTP response
     * @param chain    the remaining filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String tokenValue = header.substring(7);
            tokenService.validateToken(tokenValue).ifPresent(email -> {
                var authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));
                var auth = new UsernamePasswordAuthenticationToken(
                        email, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(auth);
            });
        }
        chain.doFilter(request, response);
    }
}

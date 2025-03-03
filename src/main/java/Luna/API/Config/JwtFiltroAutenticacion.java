package Luna.API.Config;

import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;

import java.io.IOException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import io.jsonwebtoken.Claims;
import java.util.Collection;
import java.util.Collections;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.lang.NonNull;

@Component
public class JwtFiltroAutenticacion extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFiltroAutenticacion(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request, 
        @NonNull HttpServletResponse response, 
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = obtenerToken(request);

        if (token != null && jwtUtil.validarToken(token)) {
            Authentication authentication = crearAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String obtenerToken(@NonNull HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7); // Extraemos el token, eliminando "Bearer "
        }
        return null; 
    }

    private Authentication crearAuthentication(String token) {
        Claims claims = jwtUtil.extraerClaims(token);
        return new UsernamePasswordAuthenticationToken(
            claims.getSubject(), 
            null, 
            obtenerAuthorities(claims)
        );
    }

    private Collection<? extends GrantedAuthority> obtenerAuthorities(Claims claims) {
        String rol = claims.get("rol", String.class);
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rol));
    }
}

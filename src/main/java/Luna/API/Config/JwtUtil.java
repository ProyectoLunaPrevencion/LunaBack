package Luna.API.Config;

import Luna.API.Modelo.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import javax.crypto.SecretKey;


@Component
public class JwtUtil { 
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    @Value("${jwt.secret}") // Read from application.properties
    private String secret;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        // Ensure secret is properly formatted and long enough
        byte[] keyBytes = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
            .setSubject(usuario.getEmail())
            .claim("userId", usuario.getIdUsuario()) 
            .claim("nombre", usuario.getNombre())
            .claim("apellidos", usuario.getApellidos()) 
            .claim("curso", usuario.getCurso().toString()) 
            .claim("grupo", usuario.getGrupo().toString()) 
            .claim("fechaRegistro", usuario.getFechaRegistro().toString())
            .claim("rol", usuario.getRol().toString())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .signWith(secretKey, SignatureAlgorithm.HS512)  // Usa la nueva clave segura
            .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            log.error("Token expirado: {}", ex.getMessage());
        } catch (JwtException | IllegalArgumentException ex) {
            log.error("Token inválido: {}", ex.getMessage());
        }
        return false;
    }

    public Claims extraerClaims(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
}

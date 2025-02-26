package Luna.API.Config;

import Luna.API.Modelo.Usuario;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "qazwsxedcrfv741085296309876543211324"; // 32 caracteres
    private static final long EXPIRATION_TIME = 86400000; // 1 día

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generarToken(Usuario usuario) {
        return Jwts.builder()
            .setSubject(usuario.getEmail()) // Identificador principal
            .claim("nombre", usuario.getNombre()) 
            .claim("apellidos", usuario.getApellidos()) 
            .claim("rol", usuario.getRol().toString()) 
            .claim("curso", usuario.getCurso().toString()) 
            .claim("grupo", usuario.getGrupo().toString()) 
            .claim("fechaRegistro", usuario.getFechaRegistro().toString())
            .setIssuedAt(new Date()) // Fecha de emisión
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // Expiración
            .signWith(key, SignatureAlgorithm.HS256) // Firma
            .compact();
    }

    public String extraerEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}


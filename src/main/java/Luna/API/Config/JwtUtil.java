package Luna.API.Config;

import Luna.API.Modelo.Usuario;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.util.Date;

 
 @Component
 public class JwtUtil { 
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
     private static final long EXPIRATION_TIME = 86400000; // 1 día
     private final Key key;
 
     public JwtUtil() {
         this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Genera una clave segura
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
             .signWith(key, SignatureAlgorithm.HS512)  // Usa la nueva clave segura
             .compact();
     }
 
     public boolean validarToken(String token) {
         try {
             Jwts.parserBuilder()
                 .setSigningKey(key)
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
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
     }
 }
 
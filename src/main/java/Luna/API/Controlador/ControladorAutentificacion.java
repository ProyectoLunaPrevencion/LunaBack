package Luna.API.Controlador;

import Luna.API.Modelo.Usuario;
import Luna.API.Servicio.ServicioAutentificacion;
import org.springframework.http.ResponseEntity;
import Luna.API.Config.JwtUtil;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ControladorAutentificacion {

    private final ServicioAutentificacion servicioAutentificacion;
    private final JwtUtil jwtUtil;

    public ControladorAutentificacion(ServicioAutentificacion servicioAutentificacion, JwtUtil jwtUtil) {
        this.servicioAutentificacion = servicioAutentificacion;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        try {
            String email = credenciales.get("email");
            String password = credenciales.get("password");

            // Obtener usuario validado
            Usuario usuario = servicioAutentificacion.autenticar(email, password);

            // Generar token con los datos del usuario
            String token = jwtUtil.generarToken(usuario);

            return ResponseEntity.ok(Map.of(
                "mensaje", "Inicio de sesión exitoso",
                "token", token
            ));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of(
                "error", e.getMessage()
            ));
        }
    }
}

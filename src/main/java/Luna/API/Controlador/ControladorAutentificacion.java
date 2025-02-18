package Luna.API.Controlador;

import Luna.API.Modelo.Usuario;
import Luna.API.Servicio.ServicioAutentificacion;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class ControladorAutentificacion {

    private final ServicioAutentificacion servicioAutentificacion;

    public ControladorAutentificacion(ServicioAutentificacion servicioAutentificacion) {
        this.servicioAutentificacion = servicioAutentificacion;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        try {
            String email = credenciales.get("email");
            String password = credenciales.get("password");
            Usuario usuario = servicioAutentificacion.autenticar(email, password);
            return ResponseEntity.ok(Map.of("mensaje", "Inicio de sesión exitoso", "usuario", usuario));
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
}

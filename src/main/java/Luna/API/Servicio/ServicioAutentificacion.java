package Luna.API.Servicio;

import Luna.API.Modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class ServicioAutentificacion {
    private final ServicioUsuario servicioUsuario;
    private final BCryptPasswordEncoder condificadorContrasena;

    public ServicioAutentificacion(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
        this.condificadorContrasena = new BCryptPasswordEncoder();
    }

    
    public Usuario autenticar(String email, String password) {
        Optional<Usuario> usuarioOpt = servicioUsuario.buscarPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

    
        if (!condificadorContrasena.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return usuario;
    }
}

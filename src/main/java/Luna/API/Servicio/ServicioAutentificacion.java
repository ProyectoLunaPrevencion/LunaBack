package Luna.API.Servicio;

import Luna.API.Modelo.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServicioAutentificacion {
    private final ServicioUsuario servicioUsuario;
    private final BCryptPasswordEncoder codificadorContrasena;

    public ServicioAutentificacion(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
        this.codificadorContrasena = new BCryptPasswordEncoder();
    }

    public Usuario autenticar(String email, String password) {
        Optional<Usuario> usuarioOpt = servicioUsuario.buscarPorEmail(email);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado.");
        }

        Usuario usuario = usuarioOpt.get();

        if (!codificadorContrasena.matches(password, usuario.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta.");
        }

        return usuario;
    }
}


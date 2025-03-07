package Luna.API.Controlador;

import Luna.API.Config.JwtUtil;
import Luna.API.Modelo.Usuario;
import Luna.API.Requests.UsuarioPutRequest;
import Luna.API.Servicio.ServicioUsuario;
import io.jsonwebtoken.Claims;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class ControladorUsuario {

    private final ServicioUsuario servicioUsuario;

    @Autowired
    private JwtUtil jwtUtil;

    public ControladorUsuario(ServicioUsuario servicioUsuario) {
        this.servicioUsuario = servicioUsuario;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario usuarioRegistrado = servicioUsuario.registrarUsuario(usuario);
            return ResponseEntity.ok(usuarioRegistrado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public List<Usuario> listaUsuarios() {
        return servicioUsuario.obtenerTodos();
    }
    
    @GetMapping("/current")
    public Optional<Usuario> obtenerUsuarioActual(@RequestHeader("Authorization") String bearerToken) {
        String token = bearerToken.substring(7);
        Claims tokenClaims = jwtUtil.extraerClaims(token);

        int var = (int) tokenClaims.get("userId");

        return servicioUsuario.buscarPorId(var);
    }

    @GetMapping("/{id}")
    public Optional<Usuario> obtenerUsuario(@PathVariable Integer id) {
        return servicioUsuario.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Usuario actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioPutRequest usuario) {
        System.out.println("hola");
        System.out.println(id);
        System.out.println(usuario);
        return servicioUsuario.actualizarUsuario(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Integer id) {
        servicioUsuario.eliminarUsuario(id);
    }

    @GetMapping("/buscar/{nombre}")
    public Optional<Usuario> buscarPorNombre(@PathVariable String nombre) {
        return servicioUsuario.buscarPorNombre(nombre);
    }

    @GetMapping("/buscar/apellidos/{apellidos}")
    public Optional<Usuario> buscarPorApellidos(@PathVariable String apellidos) {
        return servicioUsuario.buscarPorApellidos(apellidos);
    }

    @GetMapping("/buscar/email/{email}")
    public Optional<Usuario> buscarPorEmail(@PathVariable String email) {
        return servicioUsuario.buscarPorEmail(email);
    }

}

package Luna.API.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import Luna.API.Modelo.Usuario;
import Luna.API.Repositorio.RepositorioUsuario;
import Luna.API.Requests.UsuarioPutRequest;

@Service
public class ServicioUsuario {
    private final RepositorioUsuario repositorioUsuario;
    private final BCryptPasswordEncoder passwordEncoder;

    public ServicioUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Usuario registrarUsuario(Usuario usuario) {
        if (repositorioUsuario.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está en uso.");
        }
        
        if (usuario.getNombre() == null || usuario.getApellidos() == null ||
            usuario.getRol() == null || usuario.getCurso() == null || usuario.getGrupo() == null ||
            usuario.getTelefono() == null) {
            throw new RuntimeException("Todos los campos son obligatorios.");
        }
    
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    
        return repositorioUsuario.save(usuario);
    }
    
    public Optional<Usuario> buscarPorEmail(String email) {
        return repositorioUsuario.findByEmail(email);
    }

    public Optional<Usuario> buscarPorId(Integer id) {
        return repositorioUsuario.findById(id);
    }

    public Usuario actualizarUsuario(Integer id, UsuarioPutRequest parameters) {

        System.out.println("Hola");
        
        return repositorioUsuario.findById(id).map(usuarioFromDatabase -> {
            if (parameters.getNombre() != null && parameters.getNombre() != "") {
                usuarioFromDatabase.setNombre(parameters.getNombre());
            }
            if (parameters.getApellidos() != null && parameters.getApellidos() != "") {
                usuarioFromDatabase.setApellidos(parameters.getApellidos());
            }
            if (parameters.getTelefono() != null && parameters.getTelefono() != "") {
                usuarioFromDatabase.setTelefono(parameters.getTelefono());
            }
            if (parameters.getCurso() != null && !parameters.getCurso().toString().isEmpty()) {
                usuarioFromDatabase.setCurso(parameters.getCurso());
            }
            if (parameters.getGrupo() != null && !parameters.getGrupo().toString().isEmpty()) {
                usuarioFromDatabase.setGrupo(parameters.getGrupo());
            }

            return repositorioUsuario.save(usuarioFromDatabase);
        }).orElse(null);
    }

    public void eliminarUsuario(Integer id) {
        repositorioUsuario.deleteById(id);
    }

    public List<Usuario> obtenerTodos() {
        return repositorioUsuario.findAll();
    }

    public Optional<Usuario> buscarPorNombre(String nombre) {
        return repositorioUsuario.findByNombre(nombre);
    }

    public Optional<Usuario> buscarPorApellidos(String apellidos) {
        return repositorioUsuario.findByApellidos(apellidos);
    }
}

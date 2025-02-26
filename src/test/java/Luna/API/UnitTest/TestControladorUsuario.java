package Luna.API.UnitTest;

import Luna.API.Modelo.Usuario;
import Luna.API.Modelo.Usuario.Rol;
import Luna.API.Modelo.Usuario.Curso;
import Luna.API.Modelo.Usuario.Grupo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestControladorUsuario {

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer usuarioId;

    @SuppressWarnings("null")
    @BeforeEach
    void setup() {
        Usuario newUser = new Usuario();
        newUser.setNombre("Nuevo Usuario");
        newUser.setApellidos("Prueba Apellido");
        newUser.setEmail("nuevo@colegiovirgendelcarmen.com");
        newUser.setPassword("passwordSegura123.");
        newUser.setRol(Rol.ESTUDIANTE);
        newUser.setCurso(Curso.ESO1);
        newUser.setGrupo(Grupo.A);
        newUser.setFechaRegistro(LocalDateTime.now());

        HttpEntity<Usuario> request = new HttpEntity<>(newUser);
        ResponseEntity<Usuario> response = restTemplate.postForEntity("/api/usuarios/registro", request, Usuario.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        
        if (response.getBody() != null) {
          usuarioId = response.getBody().getIdUsuario();
        }
    }

    @Test
    void testGetUsuarios() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/usuarios", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testGetUsuarioById() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/usuarios/" + usuarioId, String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testActualizarUsuario() {
        Usuario updateUser = new Usuario();
        updateUser.setNombre("Updated Name");

        HttpEntity<Usuario> request = new HttpEntity<>(updateUser);
        restTemplate.put("/api/usuarios/" + usuarioId, request);
    }

    
    @Test
    void testBuscarPorNombre() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/usuarios/buscar/Nuevo Usuario", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testBuscarPorApellidos() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/usuarios/buscar/apellidos/Prueba Apellido", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testBuscarPorEmail() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/usuarios/buscar/email/nuevo@colegiovirgendelcarmen.com", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testEliminarUsuario() {
        restTemplate.delete("/api/usuarios/" + usuarioId);
    }
}
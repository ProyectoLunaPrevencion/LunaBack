package Luna.API.UnitTest;

import Luna.API.Modelo.PostsPizarra;
import Luna.API.Modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestControladorPizarra {

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer postPizarraId;

    @BeforeEach
    void setup() {
        PostsPizarra newPostPizarra = new PostsPizarra();
        // Se crea un usuario dummy para asociarlo al post de pizarra
        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setIdUsuario(99999);
        newPostPizarra.setUsuario(usuarioDummy);
        newPostPizarra.setContenido("Contenido de prueba");

        HttpEntity<PostsPizarra> request = new HttpEntity<>(newPostPizarra);
        ResponseEntity<PostsPizarra> response = restTemplate.postForEntity("/api/postsPizarra", request, PostsPizarra.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();

        if (response.getBody() != null) {
            postPizarraId = response.getBody().getIdPost();
        }
    }

    @Test
    void testListarPostsPizarra() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/postsPizarra", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testObtenerPostPizarraById() {
        ResponseEntity<PostsPizarra> response = restTemplate.getForEntity("/api/postsPizarra/" + postPizarraId, PostsPizarra.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testActualizarPostPizarra() {
        PostsPizarra updatePostPizarra = new PostsPizarra();
        // Se asocia nuevamente el usuario dummy
        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setIdUsuario(99999);
        updatePostPizarra.setUsuario(usuarioDummy);
        updatePostPizarra.setContenido("Contenido actualizado");

        HttpEntity<PostsPizarra> request = new HttpEntity<>(updatePostPizarra);
        restTemplate.put("/api/postsPizarra/" + postPizarraId, request);

        ResponseEntity<PostsPizarra> response = restTemplate.getForEntity("/api/postsPizarra/" + postPizarraId, PostsPizarra.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContenido()).isEqualTo("Contenido actualizado");
    }

    @Test
    void testEliminarPostPizarra() {
        restTemplate.delete("/api/postsPizarra/" + postPizarraId);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/postsPizarra/" + postPizarraId, String.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}

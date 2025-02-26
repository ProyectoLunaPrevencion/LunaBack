package Luna.API.UnitTest;

import Luna.API.Modelo.Reporte;
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
public class TestControladorReportes {

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer reporteId;

    @BeforeEach
    void setup() {
        Reporte newReporte = new Reporte();
        // Se crea un usuario dummy para asociarlo al reporte
        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setIdUsuario(99999); // id de ejemplo; debe existir en la base de datos o configurarse en el test
        newReporte.setUsuario(usuarioDummy);
        newReporte.setDescripcion("Descripción de prueba");
        newReporte.setMotivo(Reporte.MotivoReporte.OTROS);

        HttpEntity<Reporte> request = new HttpEntity<>(newReporte);
        ResponseEntity<Reporte> response = restTemplate.postForEntity("/api/reportes", request, Reporte.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();

        if (response.getBody() != null) {
            reporteId = response.getBody().getIdReporte();
        }
    }

    @Test
    void testListarReportes() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/reportes", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testObtenerReporteById() {
        ResponseEntity<Reporte> response = restTemplate.getForEntity("/api/reportes/" + reporteId, Reporte.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testActualizarReporte() {
        Reporte updateReporte = new Reporte();
        // Se asocia nuevamente el usuario dummy
        Usuario usuarioDummy = new Usuario();
        usuarioDummy.setIdUsuario(9999);
        updateReporte.setUsuario(usuarioDummy);
        updateReporte.setDescripcion("Descripción actualizada");
        updateReporte.setMotivo(Reporte.MotivoReporte.ACOSO);

        HttpEntity<Reporte> request = new HttpEntity<>(updateReporte);
        restTemplate.put("/api/reportes/" + reporteId, request);

        ResponseEntity<Reporte> response = restTemplate.getForEntity("/api/reportes/" + reporteId, Reporte.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getDescripcion()).isEqualTo("Descripción actualizada");
        assertThat(response.getBody().getMotivo()).isEqualTo(Reporte.MotivoReporte.ACOSO);
    }

    @Test
    void testEliminarReporte() {
        restTemplate.delete("/api/reportes/" + reporteId);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/reportes/" + reporteId, String.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}

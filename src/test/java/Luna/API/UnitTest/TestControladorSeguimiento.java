package Luna.API.UnitTest;

import Luna.API.Modelo.Reporte;
import Luna.API.Modelo.Seguimiento;
import Luna.API.Modelo.Seguimiento.Estado;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestControladorSeguimiento {

    @Autowired
    private TestRestTemplate restTemplate;

    private Integer seguimientoId;
    private Integer reporteId;

    @BeforeEach
    void setup() {
        // 1. Crear un reporte para poder asociarlo al seguimiento
        Reporte newReporte = new Reporte();
        newReporte.setDescripcion("Reporte de prueba");
        newReporte.setMotivo(Reporte.MotivoReporte.OTROS);

        HttpEntity<Reporte> reporteRequest = new HttpEntity<>(newReporte);
        ResponseEntity<Reporte> reporteResponse = restTemplate.postForEntity("/api/reportes", reporteRequest, Reporte.class);
        assertThat(reporteResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(reporteResponse.getBody()).isNotNull();

        if (reporteResponse.getBody() != null) {
            reporteId = reporteResponse.getBody().getIdReporte();
        }

        // 2. Crear un seguimiento asociado a ese reporte
        Seguimiento newSeguimiento = new Seguimiento();
        newSeguimiento.setReporte(reporteResponse.getBody());
        newSeguimiento.setEstado(Estado.PENDIENTE);
        newSeguimiento.setComentarios("Seguimiento inicial");
        newSeguimiento.setFechaActualizacion(LocalDateTime.now());

        HttpEntity<Seguimiento> seguimientoRequest = new HttpEntity<>(newSeguimiento);
        ResponseEntity<Seguimiento> seguimientoResponse = restTemplate.postForEntity("/api/seguimiento", seguimientoRequest, Seguimiento.class);
        assertThat(seguimientoResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(seguimientoResponse.getBody()).isNotNull();

        if (seguimientoResponse.getBody() != null) {
            seguimientoId = seguimientoResponse.getBody().getIdSeguimiento();
        }
    }

    @Test
    void testListarSeguimientos() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/seguimiento", String.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void testObtenerSeguimientoById() {
        ResponseEntity<Seguimiento> response = restTemplate.getForEntity("/api/seguimiento/" + seguimientoId, Seguimiento.class);
        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void testActualizarSeguimiento() {
        Seguimiento updateSeguimiento = new Seguimiento();
        updateSeguimiento.setReporte(new Reporte());
        updateSeguimiento.getReporte().setIdReporte(reporteId); // Mantener el reporte original
        updateSeguimiento.setEstado(Estado.EN_PROCESO);
        updateSeguimiento.setComentarios("Actualización de seguimiento");

        HttpEntity<Seguimiento> request = new HttpEntity<>(updateSeguimiento);
        restTemplate.put("/api/seguimiento/" + seguimientoId, request);

        ResponseEntity<Seguimiento> response = restTemplate.getForEntity("/api/seguimiento/" + seguimientoId, Seguimiento.class);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEstado()).isEqualTo(Estado.EN_PROCESO);
        assertThat(response.getBody().getComentarios()).isEqualTo("Actualización de seguimiento");
    }

    @Test
    void testEliminarSeguimiento() {
        restTemplate.delete("/api/seguimiento/" + seguimientoId);
        ResponseEntity<String> response = restTemplate.getForEntity("/api/seguimiento/" + seguimientoId, String.class);
        assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    }
}

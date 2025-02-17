package Luna.API.Controlador;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Luna.API.Modelo.Reporte;
import Luna.API.Servicio.ServicioReporte;

@RestController
@RequestMapping("/api/reportes")
public class ControladorReporte {

    @Autowired
    private ServicioReporte servicioReporte;

    @GetMapping
    public List<Reporte> listarReportes() {
        return servicioReporte.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<Reporte> obtenerReporte(@PathVariable Integer id) {
        return servicioReporte.obtenerPorId(id);
    }

    @PostMapping
    public Reporte crearReporte(@RequestBody Reporte reporte) {
        return servicioReporte.crearReporte(reporte);
    }

    @PutMapping("/{id}")
    public Reporte actualizarReporte(@PathVariable Integer id, @RequestBody Reporte reporte) {
        return servicioReporte.actualizarReporte(id, reporte);
    }

    @DeleteMapping("/{id}")
    public void eliminarReporte(@PathVariable Integer id) {
        servicioReporte.eliminarReporte(id);
    }
}

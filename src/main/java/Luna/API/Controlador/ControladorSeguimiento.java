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

import Luna.API.Modelo.Seguimiento;
import Luna.API.Servicio.ServicioSeguimiento;

@RestController
@RequestMapping("/api/seguimiento")
public class ControladorSeguimiento {
    @Autowired
    private ServicioSeguimiento servicioSeguimiento;

    @GetMapping
    public List<Seguimiento> listarSeguimientos() {
        return servicioSeguimiento.obtenerTodos();
    }
    
    @GetMapping("/{id}")
    public Optional<Seguimiento> obtenerSeguimiento(@PathVariable Integer id) {
        return servicioSeguimiento.obtenerPorId(id);
    }

    @PostMapping
    public Seguimiento crearSeguimiento(@RequestBody Seguimiento seguimiento) {
        return servicioSeguimiento.crearSeguimiento(seguimiento);
    }

    @PutMapping("/{id}")
    public Seguimiento actualizarSeguimiento(@PathVariable Integer id, @RequestBody Seguimiento seguimiento) {
        return servicioSeguimiento.actualizarSeguimiento(id, seguimiento);
    }

    @DeleteMapping("/{id}")
    public void eliminarSeguimiento(@PathVariable Integer id) {
        servicioSeguimiento.eliminarSeguimiento(id);
    }
   
}

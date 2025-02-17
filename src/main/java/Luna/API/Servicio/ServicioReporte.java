package Luna.API.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Luna.API.Modelo.Reporte;
import Luna.API.Repositorio.RepositorioReporte;

@Service
public class ServicioReporte {

    @Autowired
    private RepositorioReporte repositorioReporte;

    public List<Reporte> obtenerTodos() {
        return repositorioReporte.findAll();
    }

    public Optional<Reporte> obtenerPorId(Integer id) {
        return repositorioReporte.findById(id);
    }

    public Reporte crearReporte(Reporte reporte) {
        return repositorioReporte.save(reporte);
    }

    public Reporte actualizarReporte(Integer id, Reporte reporte) {
        return repositorioReporte.findById(id).map(r -> {
            r.setUsuario(reporte.getUsuario());
            r.setDescripcion(reporte.getDescripcion());
            r.setMotivo(reporte.getMotivo());
            r.setFechaReporte(reporte.getFechaReporte());
            r.setCreatedAt(reporte.getCreatedAt());
            return repositorioReporte.save(r);
        }).orElse(null);
    }

    public void eliminarReporte(Integer id) {
        repositorioReporte.deleteById(id);
    }
}

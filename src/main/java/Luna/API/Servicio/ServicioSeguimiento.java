package Luna.API.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Luna.API.Modelo.Seguimiento;
import Luna.API.Repositorio.RepositorioSeguimiento;

@Service
public class ServicioSeguimiento {

    @Autowired
    private RepositorioSeguimiento repositorioSeguimiento;

    public List<Seguimiento> obtenerTodos() {
        return repositorioSeguimiento.findAll();
    }

    public Optional<Seguimiento> obtenerPorId(Integer id) {
        return repositorioSeguimiento.findById(id);
    }

    public Seguimiento crearSeguimiento(Seguimiento seguimiento) {
        return repositorioSeguimiento.save(seguimiento);
    }

    public Seguimiento actualizarSeguimiento(Integer id, Seguimiento seguimiento) {
        return repositorioSeguimiento.findById(id).map(s -> {
            s.setReporte(seguimiento.getReporte());
            s.setEstado(seguimiento.getEstado());
            s.setComentarios(seguimiento.getComentarios());
            return repositorioSeguimiento.save(s);
        }).orElse(null);
    }

    public void eliminarSeguimiento(Integer id) {
        repositorioSeguimiento.deleteById(id);

    }

}

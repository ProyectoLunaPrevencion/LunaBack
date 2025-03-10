package Luna.API.Repositorio;
import java.util.List; 


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Luna.API.Modelo.Seguimiento;

@Repository
public interface RepositorioSeguimiento extends JpaRepository<Seguimiento, Integer> {
    Optional<Seguimiento> findByEstado(String estado);
    Optional<Seguimiento> findByFechaActualizacion(LocalDateTime fecha);
     List<Seguimiento> findByReporteIdReporte(Integer idReporte);
}

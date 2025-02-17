package Luna.API.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Luna.API.Modelo.Reporte;

@Repository
public interface RepositorioReporte extends JpaRepository<Reporte, Integer> {
    
}

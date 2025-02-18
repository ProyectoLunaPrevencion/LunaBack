package Luna.API.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Luna.API.Modelo.Seguimiento;

@Repository
public interface RepositorioSeguimiento extends JpaRepository<Seguimiento, Integer> {

}

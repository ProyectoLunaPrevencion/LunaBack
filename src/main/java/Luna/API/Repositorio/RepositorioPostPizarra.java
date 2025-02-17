package Luna.API.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Luna.API.Modelo.PostsPizarra;

@Repository
public interface RepositorioPostPizarra extends JpaRepository<PostsPizarra, Integer> {

    }




package Luna.API.Servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Luna.API.Modelo.PostsPizarra;
import Luna.API.Repositorio.RepositorioPostPizarra;

@Service
public class ServicioPostPizarra {

    @Autowired
    private RepositorioPostPizarra repositorioPostPizarra;

    public List<PostsPizarra> obtenerTodos() {
        return repositorioPostPizarra.findAll();
    }

    public Optional<PostsPizarra> obtenerPorId(Integer id) {
        return repositorioPostPizarra.findById(id);
    }

    public PostsPizarra crearPostPizarra(PostsPizarra postPizarra) {
        return repositorioPostPizarra.save(postPizarra);
    }

    public PostsPizarra actualizarPostPizarra(Integer id, PostsPizarra postPizarra) {
        return repositorioPostPizarra.findById(id).map(r -> {
            r.setUsuario(postPizarra.getUsuario());
            r.setContenido(postPizarra.getContenido());
            r.setCreatedAt(postPizarra.getCreatedAt());
            return repositorioPostPizarra.save(r);
        }).orElse(null);
    }

    public void eliminarPostPizarra(Integer id) {
        repositorioPostPizarra.deleteById(id);
    }

}

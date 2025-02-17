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

import Luna.API.Modelo.PostsPizarra;
import Luna.API.Servicio.ServicioPostPizarra;

@RestController
@RequestMapping("/api/postsPizarra")
public class ControladorPostPizarra {

    @Autowired
    private ServicioPostPizarra servicioPostPizarra;

    @GetMapping
    public List<PostsPizarra> listarPostsPizarra() {
        return servicioPostPizarra.obtenerTodos();
    }

    @GetMapping("/{id}")
    public Optional<PostsPizarra> obtenerPostPizarra(@PathVariable Integer id) {
        return servicioPostPizarra.obtenerPorId(id);
    }

    @PostMapping
    public PostsPizarra crearPostPizarra(@RequestBody PostsPizarra postPizarra) {
        return servicioPostPizarra.crearPostPizarra(postPizarra);
    }

    @PutMapping("/{id}")
    public PostsPizarra actualizarPostPizarra(@PathVariable Integer id, @RequestBody PostsPizarra postPizarra) {
        return servicioPostPizarra.actualizarPostPizarra(id, postPizarra);
    }

    @DeleteMapping("/{id}")
    public void eliminarPostPizarra(@PathVariable Integer id) {
        servicioPostPizarra.eliminarPostPizarra(id);
    }


}

package Luna.API.Modelo;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "postspizarra")

public class PostsPizarra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_post")
    private Integer idPost;


@ManyToOne
@JoinColumn(name = "id_usuario", nullable = false)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

@Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

@Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

public PostsPizarra() {
    LocalDateTime now = LocalDateTime.now();
    this.createdAt = now;
}

public PostsPizarra(Integer idPost, Usuario usuario, String contenido, LocalDateTime createdAt) {
    this.idPost = idPost;
    this.usuario = usuario;
    this.contenido = contenido;
    this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
}

//#region Getters y Setters

public Integer getIdPost() {
    return idPost;
}

public void setIdPost(Integer idPost) {
    this.idPost = idPost;
}

public Usuario getUsuario() {
    return usuario;
}

public void setUsuario(Usuario usuario) {
    this.usuario = usuario;
}

public String getContenido() {
    return contenido;
}

public void setContenido(String contenido) {
    this.contenido = contenido;
}

public LocalDateTime getCreatedAt() {
    return createdAt;
}

public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
}

//#endregion

@Override
public String toString() {
    return "PostsPizarra [contenido=" + contenido + ", createdAt=" + createdAt + ", idPost=" + idPost + ", usuario="
            + usuario + "]";

    }
}

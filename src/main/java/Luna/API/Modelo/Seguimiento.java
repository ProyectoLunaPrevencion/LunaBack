package Luna.API.Modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;

@Entity
@Table(name = "Seguimiento")
public class Seguimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_seguimiento")
    private Integer idSeguimiento;

    @ManyToOne
    @JoinColumn(name = "id_reporte", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Reporte reporte;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(columnDefinition = "TEXT")
    private String comentarios;

    @Column(name = "fecha_actualizacion", nullable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    public Seguimiento() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    public Seguimiento(Integer idSeguimiento, Reporte reporte, Estado estado, String comentarios, LocalDateTime fechaActualizacion) {
        this.idSeguimiento = idSeguimiento;
        this.reporte = reporte;
        this.estado = estado;
        this.comentarios = comentarios;
        this.fechaActualizacion = (fechaActualizacion != null) ? fechaActualizacion : LocalDateTime.now();
    }

    //#region Getters y Setters

    public Integer getIdSeguimiento() {
        return idSeguimiento;
    }

    public void setIdSeguimiento(Integer idSeguimiento) {
        this.idSeguimiento = idSeguimiento;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    //#endregion

    public enum Estado {
        PENDIENTE,
        EN_PROCESO,
        RESUELTO,
        ARCHIVADO
    }
}

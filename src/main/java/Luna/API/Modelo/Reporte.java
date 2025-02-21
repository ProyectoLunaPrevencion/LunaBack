package Luna.API.Modelo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reporte")
    private Integer idReporte;    

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private MotivoReporte motivo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DondeLoVio dondeLoVio;

    @Column(name = "fecha_reporte", nullable = false)
    private LocalDateTime fechaReporte;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Reporte() {
        LocalDateTime now = LocalDateTime.now();
        this.fechaReporte = now;
        this.createdAt = now;
    }

    public Reporte(Integer idReporte, Usuario usuario, String descripcion, MotivoReporte motivo,
                   DondeLoVio dondeLoVio, LocalDateTime fechaReporte, LocalDateTime createdAt) {
        this.idReporte = idReporte;
        this.usuario = usuario;
        this.descripcion = descripcion;
        this.motivo = motivo;
        this.dondeLoVio = dondeLoVio;
        this.fechaReporte = (fechaReporte != null) ? fechaReporte : LocalDateTime.now();
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    public enum MotivoReporte {
        ACOSO, MALESTAR_EMOCIONAL, PROBLEMA_ACADEMICO, OTROS
    }

    public enum DondeLoVio {
        EN_CLASE, EN_PATIO, EN_PASILLOS, EN_REDES_SOCIALES, OTRO
    }

    //#region Getters y Setters

    public Integer getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(Integer idReporte) {
        this.idReporte = idReporte;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public MotivoReporte getMotivo() {
        return motivo;
    }

    public void setMotivo(MotivoReporte motivo) {
        this.motivo = motivo;
    }

    public DondeLoVio getDondeLoVio() {
        return dondeLoVio;
    }

    public void setDondeLoVio(DondeLoVio dondeLoVio) {
        this.dondeLoVio = dondeLoVio;
    }

    public LocalDateTime getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDateTime fechaReporte) {
        this.fechaReporte = fechaReporte;
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
        return "Reporte{" +
                "idReporte=" + idReporte +
                ", usuario=" + usuario +
                ", descripcion='" + descripcion + '\'' +
                ", motivo=" + motivo +
                ", dondeLoVio=" + dondeLoVio +
                ", fechaReporte=" + fechaReporte +
                ", createdAt=" + createdAt +
                '}';
    }
}

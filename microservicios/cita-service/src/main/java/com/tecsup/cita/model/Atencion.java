package com.tecsup.cita.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "atencion")
public class Atencion {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_atencion")
    private Long idAtencion;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cita", nullable = false, unique = true)
    private Cita cita;
    @Lob private String diagnostico;
    @Lob private String tratamiento;
    @Lob private String observaciones;
    @Column(nullable = false)
    private LocalDateTime fechaRegistro;
    @PrePersist void registrarFecha() { if (fechaRegistro == null) fechaRegistro = LocalDateTime.now(); }
    public Long getIdAtencion() { return idAtencion; }
    public void setIdAtencion(Long idAtencion) { this.idAtencion = idAtencion; }
    public Cita getCita() { return cita; }
    public void setCita(Cita cita) { this.cita = cita; }
    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public String getTratamiento() { return tratamiento; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDateTime fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}

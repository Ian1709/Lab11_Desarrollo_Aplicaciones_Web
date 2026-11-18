package com.tecsup.medico.model;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horario_medico")
public class HorarioMedico {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_horario")
    private Long idHorario;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;
    @Column(length = 15)
    private String diaSemana;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    public Long getIdHorario() { return idHorario; }
    public void setIdHorario(Long idHorario) { this.idHorario = idHorario; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }
    public LocalTime getHoraFin() { return horaFin; }
    public void setHoraFin(LocalTime horaFin) { this.horaFin = horaFin; }
}

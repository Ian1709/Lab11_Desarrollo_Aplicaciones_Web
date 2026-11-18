package com.tecsup.cita.repository;

import com.tecsup.cita.model.Cita;
import com.tecsup.cita.model.EstadoCita;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    boolean existsByIdMedicoAndFechaAndHoraAndEstadoIn(Long idMedico, LocalDate fecha, LocalTime hora, Iterable<EstadoCita> estados);
    boolean existsByIdMedicoAndFechaAndHoraAndEstadoInAndIdCitaNot(Long idMedico, LocalDate fecha, LocalTime hora, Iterable<EstadoCita> estados, Long idCita);
}

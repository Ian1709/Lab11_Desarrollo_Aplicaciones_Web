package com.tecsup.cita.service;

import com.tecsup.cita.model.Cita;
import com.tecsup.cita.model.EstadoCita;
import com.tecsup.cita.repository.CitaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class CitaService {
    private static final Set<EstadoCita> ACTIVAS = Set.of(EstadoCita.PROGRAMADA, EstadoCita.CONFIRMADA);
    private final CitaRepository repository;
    public CitaService(CitaRepository repository) { this.repository = repository; }
    public List<Cita> listar() { return repository.findAll(); }
    public Cita buscar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cita no encontrada")); }
    public Cita crear(Cita cita) {
        if (cita.getFecha().isBefore(LocalDate.now())) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La fecha de la cita no puede estar en el pasado");
        if (ocupada(cita)) throw new ResponseStatusException(HttpStatus.CONFLICT, "El médico ya tiene una cita activa en ese horario");
        cita.setEstado(EstadoCita.PROGRAMADA);
        return repository.save(cita);
    }
    public Cita actualizarEstado(Long id, EstadoCita nuevo) {
        Cita cita = buscar(id);
        EstadoCita actual = cita.getEstado();
        boolean valido = actual == nuevo || (actual == EstadoCita.PROGRAMADA && (nuevo == EstadoCita.CONFIRMADA || nuevo == EstadoCita.CANCELADA)) || (actual == EstadoCita.CONFIRMADA && (nuevo == EstadoCita.ATENDIDA || nuevo == EstadoCita.CANCELADA));
        if (!valido) throw new ResponseStatusException(HttpStatus.CONFLICT, "Transición no permitida: " + actual + " a " + nuevo);
        cita.setEstado(nuevo);
        return repository.save(cita);
    }
    public Cita cancelar(Long id) {
        Cita cita = buscar(id);
        if (cita.getEstado() == EstadoCita.ATENDIDA) throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede cancelar una cita atendida");
        cita.setEstado(EstadoCita.CANCELADA);
        return repository.save(cita);
    }
    private boolean ocupada(Cita c) { return repository.existsByIdMedicoAndFechaAndHoraAndEstadoIn(c.getIdMedico(), c.getFecha(), c.getHora(), ACTIVAS); }
}

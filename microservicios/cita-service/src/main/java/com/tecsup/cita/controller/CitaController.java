package com.tecsup.cita.controller;

import com.tecsup.cita.model.Cita;
import com.tecsup.cita.model.EstadoCita;
import com.tecsup.cita.service.CitaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {
    private final CitaService service;
    public CitaController(CitaService service) { this.service = service; }
    @GetMapping public List<Cita> listar() { return service.listar(); }
    @GetMapping("/{id}") public Cita buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Cita crear(@Valid @RequestBody CitaRequest r) {
        Cita c = new Cita(); c.setFecha(r.fecha()); c.setHora(r.hora()); c.setIdPaciente(r.idPaciente()); c.setIdMedico(r.idMedico()); c.setMotivo(r.motivo()); return service.crear(c);
    }
    @PatchMapping("/{id}/estado") public Cita actualizarEstado(@PathVariable Long id, @Valid @RequestBody EstadoRequest r) { return service.actualizarEstado(id, r.estado()); }
    @DeleteMapping("/{id}") public Cita cancelar(@PathVariable Long id) { return service.cancelar(id); }
    public record CitaRequest(@NotNull @FutureOrPresent LocalDate fecha, @NotNull LocalTime hora,
            @NotNull @Positive Long idPaciente, @NotNull @Positive Long idMedico, @Size(max = 300) String motivo) {}
    public record EstadoRequest(@NotNull EstadoCita estado) {}
}

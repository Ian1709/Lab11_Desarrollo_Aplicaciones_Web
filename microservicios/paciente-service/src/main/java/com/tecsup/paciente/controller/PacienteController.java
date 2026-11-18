package com.tecsup.paciente.controller;

import com.tecsup.paciente.model.Paciente;
import com.tecsup.paciente.service.PacienteService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {
    private final PacienteService service;
    public PacienteController(PacienteService service) { this.service = service; }
    @GetMapping public List<Paciente> listar() { return service.listar(); }
    @GetMapping("/{id}") public Paciente buscar(@PathVariable Long id) { return service.buscar(id); }
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Paciente crear(@Valid @RequestBody PacienteRequest r) { return service.crear(map(r)); }
    @PutMapping("/{id}") public Paciente actualizar(@PathVariable Long id, @Valid @RequestBody PacienteRequest r) { return service.actualizar(id, map(r)); }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id) { service.eliminar(id); }
    private Paciente map(PacienteRequest r) {
        Paciente p = new Paciente(); p.setDni(r.dni()); p.setNombres(r.nombres()); p.setApellidos(r.apellidos());
        p.setFechaNacimiento(r.fechaNacimiento()); p.setSexo(r.sexo()); p.setTelefono(r.telefono());
        p.setDireccion(r.direccion()); p.setCorreo(r.correo()); p.setEstado(r.estado()); return p;
    }
    public record PacienteRequest(@Pattern(regexp = "\\d{8}", message = "El DNI debe contener 8 dígitos") String dni,
            @NotBlank String nombres, @NotBlank String apellidos, LocalDate fechaNacimiento,
            @Pattern(regexp = "[MF]", message = "El sexo debe ser M o F") String sexo,
            String telefono, String direccion, @Email String correo,
            @Pattern(regexp = "[AI]", message = "El estado debe ser A o I") String estado) {}
}

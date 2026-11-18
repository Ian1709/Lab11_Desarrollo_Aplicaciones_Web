package com.tecsup.paciente.service;

import com.tecsup.paciente.model.Paciente;
import com.tecsup.paciente.repository.PacienteRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class PacienteService {
    private final PacienteRepository repository;
    public PacienteService(PacienteRepository repository) { this.repository = repository; }
    public List<Paciente> listar() { return repository.findAll(); }
    public Paciente buscar(Long id) { return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paciente no encontrado")); }
    public Paciente crear(Paciente paciente) {
        if (paciente.getDni() != null && repository.existsByDni(paciente.getDni())) throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya está registrado");
        paciente.setEstado("A");
        try { return repository.save(paciente); } catch (DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya está registrado"); }
    }
    public Paciente actualizar(Long id, Paciente datos) {
        Paciente actual = buscar(id);
        if (datos.getDni() != null && repository.existsByDniAndIdPacienteNot(datos.getDni(), id)) throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya está registrado");
        actual.setDni(datos.getDni()); actual.setNombres(datos.getNombres()); actual.setApellidos(datos.getApellidos());
        actual.setFechaNacimiento(datos.getFechaNacimiento()); actual.setSexo(datos.getSexo()); actual.setTelefono(datos.getTelefono());
        actual.setDireccion(datos.getDireccion()); actual.setCorreo(datos.getCorreo());
        if (datos.getEstado() != null) actual.setEstado(datos.getEstado());
        return repository.save(actual);
    }
    public void eliminar(Long id) { repository.delete(buscar(id)); }
}

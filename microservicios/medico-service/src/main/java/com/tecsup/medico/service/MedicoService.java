package com.tecsup.medico.service;

import com.tecsup.medico.model.Especialidad;
import com.tecsup.medico.model.Medico;
import com.tecsup.medico.repository.EspecialidadRepository;
import com.tecsup.medico.repository.MedicoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class MedicoService {
    private final MedicoRepository medicos;
    private final EspecialidadRepository especialidades;
    public MedicoService(MedicoRepository medicos, EspecialidadRepository especialidades) { this.medicos = medicos; this.especialidades = especialidades; }
    public List<Especialidad> listarEspecialidades() { return especialidades.findAll(); }
    public Especialidad buscarEspecialidad(Long id) { return especialidades.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Especialidad no encontrada")); }
    public Especialidad crearEspecialidad(Especialidad e) { e.setEstado("A"); return especialidades.save(e); }
    public Especialidad actualizarEspecialidad(Long id, Especialidad e) { Especialidad actual = buscarEspecialidad(id); actual.setNombre(e.getNombre()); actual.setDescripcion(e.getDescripcion()); if(e.getEstado()!=null) actual.setEstado(e.getEstado()); return especialidades.save(actual); }
    public void eliminarEspecialidad(Long id) { try { especialidades.delete(buscarEspecialidad(id)); } catch (DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT, "No se puede eliminar una especialidad asignada a médicos"); } }
    public List<Medico> listarMedicos() { return medicos.findAll(); }
    public Medico buscarMedico(Long id) { return medicos.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Médico no encontrado")); }
    public Medico crearMedico(Medico m, Long especialidadId) { if (m.getCmp()!=null && medicos.existsByCmp(m.getCmp())) throw new ResponseStatusException(HttpStatus.CONFLICT,"El CMP ya está registrado"); m.setEspecialidad(buscarEspecialidad(especialidadId)); m.setEstado("A"); try { return medicos.save(m); } catch(DataIntegrityViolationException ex) { throw new ResponseStatusException(HttpStatus.CONFLICT,"El CMP ya está registrado"); } }
    public Medico actualizarMedico(Long id, Medico datos, Long especialidadId) { Medico actual=buscarMedico(id); if(datos.getCmp()!=null && medicos.existsByCmpAndIdMedicoNot(datos.getCmp(),id)) throw new ResponseStatusException(HttpStatus.CONFLICT,"El CMP ya está registrado"); actual.setCmp(datos.getCmp()); actual.setNombres(datos.getNombres()); actual.setApellidos(datos.getApellidos()); actual.setTelefono(datos.getTelefono()); actual.setCorreo(datos.getCorreo()); actual.setEspecialidad(buscarEspecialidad(especialidadId)); if(datos.getEstado()!=null) actual.setEstado(datos.getEstado()); return medicos.save(actual); }
    public void eliminarMedico(Long id) { medicos.delete(buscarMedico(id)); }
}

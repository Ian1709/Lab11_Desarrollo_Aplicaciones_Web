package com.tecsup.medico.controller;

import com.tecsup.medico.model.Especialidad;
import com.tecsup.medico.service.MedicoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {
    private final MedicoService service;
    public EspecialidadController(MedicoService service) { this.service=service; }
    @GetMapping public List<Especialidad> listar(){return service.listarEspecialidades();}
    @GetMapping("/{id}") public Especialidad buscar(@PathVariable Long id){return service.buscarEspecialidad(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Especialidad crear(@Valid @RequestBody EspecialidadRequest r){Especialidad e=new Especialidad();e.setNombre(r.nombre());e.setDescripcion(r.descripcion());return service.crearEspecialidad(e);}
    @PutMapping("/{id}") public Especialidad actualizar(@PathVariable Long id,@Valid @RequestBody EspecialidadRequest r){Especialidad e=new Especialidad();e.setNombre(r.nombre());e.setDescripcion(r.descripcion());e.setEstado(r.estado());return service.actualizarEspecialidad(id,e);}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){service.eliminarEspecialidad(id);}
    public record EspecialidadRequest(@NotBlank String nombre,String descripcion,@Pattern(regexp="[AI]") String estado){}
}

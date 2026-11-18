package com.tecsup.medico.controller;

import com.tecsup.medico.model.Medico;
import com.tecsup.medico.service.MedicoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {
    private final MedicoService service;
    public MedicoController(MedicoService service){this.service=service;}
    @GetMapping public List<Medico> listar(){return service.listarMedicos();}
    @GetMapping("/{id}") public Medico buscar(@PathVariable Long id){return service.buscarMedico(id);}
    @PostMapping @ResponseStatus(HttpStatus.CREATED) public Medico crear(@Valid @RequestBody MedicoRequest r){return service.crearMedico(map(r),r.idEspecialidad());}
    @PutMapping("/{id}") public Medico actualizar(@PathVariable Long id,@Valid @RequestBody MedicoRequest r){return service.actualizarMedico(id,map(r),r.idEspecialidad());}
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void eliminar(@PathVariable Long id){service.eliminarMedico(id);}
    private Medico map(MedicoRequest r){Medico m=new Medico();m.setCmp(r.cmp());m.setNombres(r.nombres());m.setApellidos(r.apellidos());m.setTelefono(r.telefono());m.setCorreo(r.correo());m.setEstado(r.estado());return m;}
    public record MedicoRequest(@NotBlank String cmp,@NotBlank String nombres,@NotBlank String apellidos,String telefono,@Email String correo,Long idEspecialidad,@Pattern(regexp="[AI]") String estado){}
}

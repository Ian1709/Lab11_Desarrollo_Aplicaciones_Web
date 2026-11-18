package com.tecsup.medico.repository;

import com.tecsup.medico.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    boolean existsByCmp(String cmp);
    boolean existsByCmpAndIdMedicoNot(String cmp, Long idMedico);
}

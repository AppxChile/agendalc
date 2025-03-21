package com.agendalc.agendalc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.Agenda;
import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.entities.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    long countByAgendaAndBloqueHorario(Agenda agenda, BloqueHorario bloqueHorario);

    List<Cita> findByBloqueHorario(BloqueHorario bloqueHorario);

    List<Cita> findByRut(Integer rut);

}

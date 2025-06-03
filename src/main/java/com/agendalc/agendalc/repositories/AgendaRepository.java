package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

}

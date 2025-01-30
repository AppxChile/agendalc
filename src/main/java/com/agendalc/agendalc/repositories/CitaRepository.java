package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita,Long> {

}

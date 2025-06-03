package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.agendalc.agendalc.entities.Tramite;

@Repository
public interface TramiteRepository extends JpaRepository<Tramite, Long> {

}

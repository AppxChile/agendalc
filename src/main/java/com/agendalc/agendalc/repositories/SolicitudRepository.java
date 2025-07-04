package com.agendalc.agendalc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.agendalc.agendalc.entities.Solicitud;

import java.util.List;
import java.time.LocalDate;

public interface SolicitudRepository extends JpaRepository<Solicitud, Long> {
    List<Solicitud> findByEstado(Solicitud.EstadoSolicitud estado);

    List<Solicitud> findByRut(Integer rut);

    List<Solicitud> findByAsignadoA(String rut);

    @Query("SELECT s FROM Solicitud s WHERE FUNCTION('YEAR', s.fechaSolicitud) = :year")
    List<Solicitud> findByFechaSolcitudYear(int year);

    @Query("SELECT s FROM Solicitud s LEFT JOIN FETCH s.movimientos m WHERE YEAR(s.fechaSolicitud) = :year ORDER BY m.fechaMovimiento ASC")
    List<Solicitud> findByFechaSolicitudYearWithMovimientosOrdered(@Param("year") int year);

    List<Solicitud> findByFechaSolicitudBetweenAndEstado(LocalDate fechaInicio, LocalDate fechaFin,
            Solicitud.EstadoSolicitud estado);

    List<Solicitud> findByFechaSolicitudBetween(LocalDate fechaInicio, LocalDate fechaFin);

}

package com.agendalc.agendalc.services.interfaces;

import java.util.List;
import java.util.Map;

import com.agendalc.agendalc.entities.BloqueHorario;

public interface BloqueHorarioService {

    BloqueHorario createBloqueHorario(BloqueHorario bloqueHorario);

    List<BloqueHorario> getAllBloquesHorarios();

    BloqueHorario getBloqueHorarioById(Long id);

    BloqueHorario updateBloqueHorario(Long id, BloqueHorario bloqueHorarioActualizado);

    void deleteBloqueHorarioById(Long id);

    BloqueHorario save(BloqueHorario bloqueHorario);

    BloqueHorario findById(Long id);

    void delete(BloqueHorario bloqueHorario);

    Map<Long, BloqueHorario> findAllById(Iterable<Long> ids);

    <S extends BloqueHorario> List<S> saveAll(Iterable<S> entities);

}

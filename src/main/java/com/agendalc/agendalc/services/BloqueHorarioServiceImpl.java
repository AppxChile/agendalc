package com.agendalc.agendalc.services;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agendalc.agendalc.entities.BloqueHorario;
import com.agendalc.agendalc.repositories.BloqueHorarioRepository;
import com.agendalc.agendalc.services.interfaces.BloqueHorarioService;
import com.agendalc.agendalc.utils.RepositoryUtils;

@Service
public class BloqueHorarioServiceImpl implements BloqueHorarioService {

    private final BloqueHorarioRepository bloqueHorarioRepository;

    public BloqueHorarioServiceImpl(BloqueHorarioRepository bloqueHorarioRepository) {
        this.bloqueHorarioRepository = bloqueHorarioRepository;
    }

    @Override
    public BloqueHorario createBloqueHorario(BloqueHorario bloqueHorario) {
        return bloqueHorarioRepository.save(bloqueHorario);
    }

    @Override
    public List<BloqueHorario> getAllBloquesHorarios() {
        return bloqueHorarioRepository.findAll();
    }

    @Override
    public BloqueHorario getBloqueHorarioById(Long id) {
        return RepositoryUtils.findOrThrow(bloqueHorarioRepository.findById(id),
                String.format("No se encontro el bloque hroario %d", id));

    }

    @Transactional
    @Override
    public BloqueHorario updateBloqueHorario(Long id, BloqueHorario bloqueHorarioActualizado) {
        BloqueHorario bloqueExistente = getBloqueHorarioById(id);

        bloqueExistente.setHoraInicio(bloqueHorarioActualizado.getHoraInicio());
        bloqueExistente.setHoraFin(bloqueHorarioActualizado.getHoraFin());
        bloqueExistente.setCuposDisponibles(bloqueHorarioActualizado.getCuposDisponibles());

        return bloqueHorarioRepository.save(bloqueExistente);
    }

    @Override
    public void deleteBloqueHorarioById(Long id) {
        BloqueHorario bloqueHorario = getBloqueHorarioById(id);
        bloqueHorarioRepository.delete(bloqueHorario);
    }

    @Override
    public BloqueHorario save(BloqueHorario bloqueHorario) {
        return bloqueHorarioRepository.save(bloqueHorario);
    }

    @Override
    public BloqueHorario findById(Long id) {

        return RepositoryUtils.findOrThrow(bloqueHorarioRepository.findById(id),
                String.format("No se encontro el bloque horario %d", id));

    }

    @Override
    public void delete(BloqueHorario bloqueHorario) {
        bloqueHorarioRepository.delete(bloqueHorario);
    }

    @Override
    public Map<Long, BloqueHorario> findAllById(Iterable<Long> ids) {
        List<BloqueHorario> bloques = bloqueHorarioRepository.findAllById(ids);
        return bloques.stream().collect(Collectors.toMap(BloqueHorario::getIdBloque, bloque -> bloque));
    }

    @Override
    public <S extends BloqueHorario> List<S> saveAll(Iterable<S> entities) {
        return bloqueHorarioRepository.saveAll(entities);
    }

}

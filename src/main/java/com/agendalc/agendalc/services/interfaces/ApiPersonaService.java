package com.agendalc.agendalc.services.interfaces;

import com.agendalc.agendalc.dto.PersonaResponse;

public interface ApiPersonaService {

    PersonaResponse getPersonaInfo(Integer rut);

}

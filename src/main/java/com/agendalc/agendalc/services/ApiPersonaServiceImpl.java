package com.agendalc.agendalc.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.agendalc.agendalc.config.ApiProperties;
import com.agendalc.agendalc.dto.PersonaResponse;
import com.agendalc.agendalc.services.interfaces.ApiPersonaService;

@Service
public class ApiPersonaServiceImpl implements ApiPersonaService {

    private final WebClient webClientPersona;

    public ApiPersonaServiceImpl(WebClient.Builder webClientBuilder, ApiProperties apiProperties) {
        this.webClientPersona = webClientBuilder.baseUrl(apiProperties.getPersonaUrl()).build();
    }

    @Override
    public PersonaResponse getPersonaInfo(Integer rut) {
        return webClientPersona.get()
                .uri("/{rut}", rut)
                .retrieve()
                .bodyToMono(PersonaResponse.class)
                .block();
    }

}

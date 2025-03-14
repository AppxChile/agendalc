package com.agendalc.agendalc.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.MediaType;

import com.agendalc.agendalc.config.ApiProperties;
import com.agendalc.agendalc.dto.PersonaResponse;

import reactor.core.publisher.Mono;

@Service
public class ApiService {

    private final WebClient webClientPersona;
    private final WebClient webClientMail;

    public ApiService(WebClient.Builder webClientBuilder, ApiProperties apiProperties) {
        this.webClientPersona = webClientBuilder.baseUrl(apiProperties.getPersonaUrl()).build();
        this.webClientMail = webClientBuilder.baseUrl(apiProperties.getMailUrl()).build();
    }

    public PersonaResponse getPersonaInfo(Integer rut) {
        return webClientPersona.get()
                .uri("/{rut}", rut) // Se agrega el RUT a la URL
                .retrieve()
                .bodyToMono(PersonaResponse.class)
                .block(); // Bloquea hasta recibir la respuesta (sincrónico)
    }

    public void sendEmail(String to, String subject, String templateName, Map<String, Object> variables) {
        try {
            webClientMail.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/send")
                            .queryParam("to", to)
                            .queryParam("subject", subject)
                            .queryParam("templateName", templateName)
                            .build())
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(variables) // JSON con variables para la plantilla
                    .retrieve()
                    .onStatus(
                            HttpStatusCode::isError,
                            response -> response.bodyToMono(String.class)
                                    .flatMap(error -> Mono.error(new RuntimeException("Error en la API: " + error))))
                    .bodyToMono(Void.class)
                    .block();

        } catch (WebClientResponseException e) {
            HashMap<String, String> response = new HashMap<>();
            response.put("message", e.getMessage());
        }
    }
}

package com.agendalc.agendalc.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.agendalc.agendalc.config.ApiProperties;
import com.agendalc.agendalc.services.interfaces.ApiMailService;

import reactor.core.publisher.Mono;

@Service
public class ApiMailServiceImpl implements ApiMailService {

    private final WebClient webClientMail;

    public ApiMailServiceImpl(WebClient.Builder webClientBuilder, ApiProperties apiProperties) {
        this.webClientMail = webClientBuilder.baseUrl(apiProperties.getMailUrl()).build();
    }

    @Override
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

package com.agendalc.agendalc.config;

import org.springframework.stereotype.Component;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Component
@ConfigurationProperties(prefix = "api")
public class ApiProperties {

    private String personaUrl;

    private String mailUrl;

    public String getPersonaUrl() {
        return personaUrl;
    }

    public void setPersonaUrl(String personaUrl) {
        this.personaUrl = personaUrl;
    }

    public String getMailUrl() {
        return mailUrl;
    }

    public void setMailUrl(String usuariosUrl) {
        this.mailUrl = usuariosUrl;
    }

}
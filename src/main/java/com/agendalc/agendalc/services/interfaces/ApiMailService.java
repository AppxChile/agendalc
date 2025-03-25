package com.agendalc.agendalc.services.interfaces;

import java.util.Map;

public interface ApiMailService {

    void sendEmail(String to, String subject, String templateName, Map<String, Object> variables);

}

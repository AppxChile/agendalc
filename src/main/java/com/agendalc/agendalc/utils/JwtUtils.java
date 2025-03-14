package com.agendalc.agendalc.utils;

import org.springframework.stereotype.Component;

import com.agendalc.agendalc.config.JwtProperties;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtils {

    private final SecretKey secretKey;

    public JwtUtils(JwtProperties jwtProperties) {
        this.secretKey = new SecretKeySpec(jwtProperties.getSecret().getBytes(), "HmacSHA256");
    }

    public SecretKey getSecretKey() {
        return secretKey;
    }
}

package com.agendalc.agendalc.security;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.agendalc.agendalc.security.filter.JwtValidationFilter;
import com.agendalc.agendalc.utils.JwtUtils;

@Configuration
public class SpringSecurityConfig {


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
SecurityFilterChain filterChain(HttpSecurity http, JwtUtils jwtUtils) throws Exception {

    http.cors(cors -> cors.disable())
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(authz -> authz
            .anyRequest().authenticated()
        )
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .addFilterBefore(new JwtValidationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class); // ✅ Añadir con orden

    return http.build();
}
}

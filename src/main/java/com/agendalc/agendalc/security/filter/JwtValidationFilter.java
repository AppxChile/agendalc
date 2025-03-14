package com.agendalc.agendalc.security.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agendalc.agendalc.security.SimpleGrantedAuthorityJsonCreator;
import com.agendalc.agendalc.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    public JwtValidationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // Obtener el token del encabezado Authorization
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            // Validar y extraer claims del token
            Claims claims = Jwts.parser()
                    .verifyWith(jwtUtils.getSecretKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String username = claims.getSubject();

            if (username != null) {
                // Extraer roles (authorities) del token
                Object authoritiesClaims = claims.get("authorities");

                // Convertir el string de authorities a una lista de GrantedAuthority
                Collection<? extends GrantedAuthority> authorities = Arrays.asList(
                        new ObjectMapper()
                                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityJsonCreator.class)
                                .readValue(authoritiesClaims.toString().getBytes(), SimpleGrantedAuthority[].class));

                // Crear objeto de autenticación con las autoridades
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        username, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Establecer usuario autenticado en el contexto de seguridad
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        } catch (SecurityException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Token inválido o expirado.");
            return;
        }

        chain.doFilter(request, response);
    }
}
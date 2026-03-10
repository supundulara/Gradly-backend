package com.Gradly.gateway_service;// java


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // allow any localhost port (http/https) and 127.0.0.1
        config.addAllowedOriginPattern("*");
        config.addAllowedMethod("*");          // allow all HTTP methods including POST, PUT, PATCH, DELETE, OPTIONS
        config.addAllowedHeader("*");          // allow any header
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);
        // Optionally expose headers if your frontend needs them:
        config.addExposedHeader("Authorization");
        config.addExposedHeader("Location");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}

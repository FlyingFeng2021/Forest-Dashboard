package com.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        final String ORIGINS[] = new String[] { "GET", "POST", "PUT", "DELETE" };
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods(ORIGINS)
                .allowCredentials(true)
                .maxAge(3600);
    }
}

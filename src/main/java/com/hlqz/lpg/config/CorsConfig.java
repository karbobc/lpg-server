package com.hlqz.lpg.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

/**
 * @author Karbob
 * @date 2024-01-06
 *
 * 跨域支持
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")
            .allowedHeaders("*")
            .exposedHeaders("*")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(Duration.ofHours(1).toSeconds());
    }
}

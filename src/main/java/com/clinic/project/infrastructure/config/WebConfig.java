package com.clinic.project.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Configure Spring to serve static resources (HTML, CSS, JS, etc.)
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/", "classpath:/public/");
        
        // Optionally, configure a specific location for HTML files
        registry.addResourceHandler("/html/**")
                .addResourceLocations("classpath:/static/");
    }
}

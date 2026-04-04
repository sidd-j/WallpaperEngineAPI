package com.sid.Store.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

        @Value("${image.dir:/app/apod-images/}")
        private String IMAGE_DIR;

        @Override
        public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/apod-images/**")
                        .addResourceLocations("file:" + IMAGE_DIR);
        }
}
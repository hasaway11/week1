package com.example.week1;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 스프링 설정 파일
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 서버의 모든 주소에 대하여
        // 모든 클라이언트, 모든 메소드, 모든 헤더에 대해 접근을 허용하겠다. -> 보안 X까 설정
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("*")
                .allowedHeaders("*").allowCredentials(false).maxAge(3600);
    }
}

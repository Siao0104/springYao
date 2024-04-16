package com.example.spring_yao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //設置允許跨域的路徑
        registry.addMapping("/**")
                //允許來源
                .allowedOriginPatterns("*")
                //允許所有頭的訊息
                .allowedHeaders(CorsConfiguration.ALL)
                //允許所有方法(GET、POST...等)
                .allowedMethods(CorsConfiguration.ALL)
                //允許前端帶上憑證
                .allowCredentials(true)
                //1小時內不需重新驗證
                .maxAge(3600);
    }
}

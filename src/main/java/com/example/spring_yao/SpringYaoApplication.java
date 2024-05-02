package com.example.spring_yao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaAuditing
@EnableScheduling
public class SpringYaoApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringYaoApplication.class, args);
		String port = context.getEnvironment().getProperty("server.port");
		System.out.println(STR."Swagger UI : http://localhost:\{port}/swagger-ui/index.html");
	}

}

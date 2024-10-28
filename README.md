# springBoot專案建置

## 1. 專案生成方式

- IDEA創建
- Spring initializr
- STS創建

## 2. 環境/系統配置

- pom.xml
  - 相關依賴配置(swagger、各系統模組...等)
- resources/application.properties
  - 各項環境配置

## 3. properties配置

- application

```properties
spring.application.name=springYao
# 應用服務
server.tomcat.uri-encoding=UTF-8
server.port=8080

# DB連線帳號
spring.datasource.username=yao
# DB連線密碼
spring.datasource.password=yao0104
# mySql一般連線
#spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
#spring.datasource.url=jdbc:mysql://127.0.0.1:3306/Yao?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8
# mySql連線(log4)
spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
spring.datasource.url=jdbc:log4jdbc:mysql://192.168.0.10:3306/Yao?useUnicode=true&characterEncoding=utf-8&useSSL=false&allowMultiQueries=true&serverTimezone=GMT%2B8

# 針對DB session處理，簡單CRUD可開啟，複雜邏輯建議關閉
spring.jpa.open-in-view=false
# JAP指定DB
spring.jpa.database=mysql
# 是否打印SQL
spring.jpa.show-sql=true

# rabbitMq配置
spring.rabbitmq.host=192.168.0.10
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

# 是否啟動ACK(auto,manual)
spring.rabbitmq.listener.simple.acknowledge-mode=auto
spring.rabbitmq.listener.simple.default-requeue-rejected=false
# 重試機制
spring.rabbitmq.listener.simple.retry.enabled=true
# 重試機制次數
spring.rabbitmq.listener.simple.retry.max-attempts=5
# 重試機制延遲秒數
spring.rabbitmq.listener.simple.retry.initial-interval=3000

# rabbitMq傳輸資料信任格式
rabbitmq.trusted.packages=com.example.spring_yao

# feign WS註冊中心
#eureka.client.service-url.defaultZone=http://localhost:1000/eureka

# kafka配置
spring.kafka.bootstrap-servers=192.168.0.10:9092
spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.group-id=kafkaYao
spring.kafka.consumer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.consumer.value-serializer=org.apache.kafka.common.serialization.StringSerializer

# oauth2配置
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=shiny7723@gmail.com
spring.mail.password=tzcgpuycjyblhrvr
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

#redis配置
spring.data.redis.host=192.168.0.10
spring.data.redis.port=6379
```

- log4jdbc.log4j2

```properties
log4jdbc.spylogdelegator.name=net.sf.log4jdbc.log.slf4j.Slf4jSpyLogDelegator

logging:
  level:
    org.springframework.security:debug&info
    org.springframework.web: error
    org.hibernate.engine.QueryParameters: debug
    org.hibernate.engine.query.HQLQueryPlan: debug
    org.hibernate.type.descriptor.sql.BasicBinder: trace
```



## 4. pom

```xml
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<!--tomcat-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-tomcat</artifactId>
		</dependency>
		<!--jdbc-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-jdbc</artifactId>
		</dependency>
		<!--lombok-->
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
		</dependency>
		<!--JPA-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<!--JPA LOG日誌使用-->
		<dependency>
			<groupId>org.bgee.log4jdbc-log4j2</groupId>
			<artifactId>log4jdbc-log4j2-jdbc4.1</artifactId>
			<version>1.16</version>
		</dependency>
		<!--openapi swagger-->
		<dependency>
			<groupId>org.springdoc</groupId>
			<artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
			<version>2.2.0</version>
		</dependency>
		<!--valid-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-validation</artifactId>
		</dependency>
		<!--security-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-security</artifactId>
		</dependency>
		<!--mysql-->
		<dependency>
			<groupId>com.mysql</groupId>
			<artifactId>mysql-connector-j</artifactId>
			<scope>runtime</scope>
		</dependency>
		<!--jwt-->
		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-api</artifactId>
			<version>0.11.5</version>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-impl</artifactId>
			<version>0.11.5</version>
			<scope>runtime</scope>
		</dependency>

		<dependency>
			<groupId>io.jsonwebtoken</groupId>
			<artifactId>jjwt-jackson</artifactId>
			<version>0.11.5</version>
			<scope>runtime</scope>
		</dependency>
		<!--feign-->
		<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-openfeign</artifactId>
		</dependency>
		<!--rabbit-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
		</dependency>
		<!--h2-->
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<!--quartz-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-quartz</artifactId>
		</dependency>
		<!--kafka-->
		<dependency>
			<groupId>org.springframework.kafka</groupId>
			<artifactId>spring-kafka</artifactId>
		</dependency>
		<!--hotswap-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<optional>true</optional>
		</dependency>
		<!--		<dependency>-->
<!--			<groupId>org.springframework.cloud</groupId>-->
<!--			<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>-->
<!--			<version>2.1.1.RELEASE</version>-->
<!--		</dependency>-->
		<!--mail-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-mail</artifactId>
		</dependency>
		<!--oauth2-->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-oauth2-client</artifactId>
		</dependency>
		<!-- Google API 客戶端 (可選，如果需要存取更多 Google API) -->
		<dependency>
			<groupId>com.google.api-client</groupId>
			<artifactId>google-api-client</artifactId>
			<version>1.33.0</version>
		</dependency>
		<!-- redis -->
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-redis</artifactId>
		</dependency>
```

## 5. config

### 1. 允許前端跨域使用後端

```java
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

```

### 2. Swagger OpenAPI

```java
package com.example.spring_yao.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI(){
        final String securitySchemeName = "bearerAuth";
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
                                .name(securitySchemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")))
                .info(new Info().title("Swagger OpenAPI")
                        .description("Yao的專案")
                        .version("v1")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
```

### 3. SpringSecurity

```java
package com.example.spring_yao.config;

import com.example.spring_yao.utils.jwt.JwtAuthenticationFilterService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationProvider authenticationProvider(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder
    ) {
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilterService authFilter) throws Exception {
        http
                .authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers("/spring_yao/userBasic/uiRegisterUser","/spring_yao/userBasic/uiUserLogging").permitAll()
                                .requestMatchers("/spring_yao/codeMst/**").hasAnyAuthority("ADMIN")
                                .requestMatchers("/spring_yao/codeDtl/**").permitAll()
                                .requestMatchers("/swagger-ui/index.html/**").permitAll()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                //Spring Security CSRF預設保護PATCH，POST，PUT，DELETE
                //不保護GET，HEAD，TRACE，OPTIONS等Safe Methods
                .csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
```

### 4. RedisConfig

```java
//使用.opsForValue則用StringRedisTemplate即可
//使用.opsForHash需建立以下config

@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
```
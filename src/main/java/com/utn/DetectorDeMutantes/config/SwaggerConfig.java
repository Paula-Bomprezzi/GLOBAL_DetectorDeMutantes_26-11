package com.utn.DetectorDeMutantes.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {
        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI()
                    .info(new Info()
                            .title("API de detección de mutantes")
                            .version("1.0.0")
                            .description("API REST para detectar mutantes y obtener estadísticas")
                            .contact(new Contact()
                                    .name("Paula Bomprezzi")
                                    .email("paulabomp@gmail.com")));
        }
    }







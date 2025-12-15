package com.health.tools.cards;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Aplicación principal del microservicio Cards Service.
 * Gestiona tarjetas de terapia de forma independiente.
 */
@SpringBootApplication(scanBasePackages = {
    "com.health.tools.cards",
    "com.health.tools.common"
})
public class CardsServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CardsServiceApplication.class, args);
    }
}

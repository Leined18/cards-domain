package com.health.tools.cards.infrastructure.config;

import com.health.tools.cards.domain.service.CardFactory;
import com.health.tools.cards.domain.service.prototype.CardPrototype;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de beans para el dominio de tarjetas.
 */
@Configuration
public class CardConfiguration {
    
    /**
     * Crea el factory de tarjetas con todos los prototipos registrados.
     */
    @Bean
    public CardFactory cardFactory(List<CardPrototype> prototypes) {
        return new CardFactory(prototypes);
    }
}

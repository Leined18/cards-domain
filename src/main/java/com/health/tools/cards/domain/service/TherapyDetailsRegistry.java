package com.health.tools.cards.domain.service;

import com.health.tools.cards.domain.model.TherapyType;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Registry central de factories de detalles por tipo de terapia.
 * Implementa el patrón Registry para permitir extensión dinámica.
 * Uso: inyectar en servicios de aplicación para crear detalles validados.
 */
@Component
public class TherapyDetailsRegistry {
    private final Map<TherapyType, TherapyDetailsFactory> factories = new HashMap<>();

    /**
     * Registra un factory para un tipo de terapia.
     * 
     * @param factory factory a registrar
     */
    public void register(TherapyDetailsFactory factory) {
        factories.put(factory.getTherapyType(), factory);
    }

    /**
     * Obtiene el factory para un tipo de terapia.
     * 
     * @param type tipo de terapia
     * @return factory registrado
     * @throws IllegalArgumentException si no hay factory registrado
     */
    public TherapyDetailsFactory get(TherapyType type) {
        TherapyDetailsFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("No hay factory registrado para tipo de terapia: " + type);
        }
        return factory;
    }

    /**
     * Verifica si hay factory registrado para un tipo.
     * 
     * @param type tipo de terapia
     * @return true si existe factory
     */
    public boolean isRegistered(TherapyType type) {
        return factories.containsKey(type);
    }

    /**
     * Retorna todos los tipos de terapia registrados.
     * 
     * @return colección inmutable de tipos
     */
    public Collection<TherapyType> getRegisteredTypes() {
        return Collections.unmodifiableCollection(factories.keySet());
    }
}

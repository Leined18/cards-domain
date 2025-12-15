package com.health.tools.cards.domain.service;

import com.health.tools.cards.domain.model.TherapyType;
import java.util.Map;

/**
 * Factory para crear y validar detalles específicos de terapia.
 * Implementa el patrón Factory Method para permitir extensión.
 */
public interface TherapyDetailsFactory {
    /**
     * Crea detalles de terapia validados a partir de datos raw.
     * 
     * @param rawData datos sin validar
     * @return mapa con detalles validados
     * @throws IllegalArgumentException si los datos son inválidos
     */
    Map<String, Object> create(Map<String, Object> rawData) throws IllegalArgumentException;

    /**
     * Retorna el esquema de validación (meta-information) para esta terapia.
     * 
     * @return esquema JSON Schema o similar
     */
    Map<String, Object> getSchema();

    /**
     * Retorna el tipo de terapia que este factory maneja.
     * 
     * @return tipo de terapia
     */
    TherapyType getTherapyType();
}

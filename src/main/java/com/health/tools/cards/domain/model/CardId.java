package com.health.tools.cards.domain.model;

import com.health.tools.common.domain.ValueObject;
import java.util.Objects;
import java.util.UUID;

/**
 * Value Object que representa el identificador único de una tarjeta de terapia.
 * Inmutable, sin identidad propia, comparable por valor.
 */
public final class CardId implements ValueObject {
    private final String value;

    private CardId(String value) {
        this.value = Objects.requireNonNull(value, "CardId cannot be null");
    }

    /**
     * Crea un CardId a partir de un string.
     * 
     * @param value identificador en formato string
     * @return nuevo CardId
     */
    public static CardId of(String value) {
        return new CardId(value);
    }

    /**
     * Genera un CardId aleatorio usando UUID.
     * 
     * @return nuevo CardId con UUID generado
     */
    public static CardId generate() {
        return new CardId(UUID.randomUUID().toString());
    }

    /**
     * Retorna el valor del identificador.
     * 
     * @return string del ID
     */
    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CardId cardId))
            return false;
        return Objects.equals(value, cardId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}

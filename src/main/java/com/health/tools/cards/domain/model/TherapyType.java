package com.health.tools.cards.domain.model;

/**
 * Enumeración de tipos de terapias soportadas en la plataforma.
 * Extensible: agregar nuevos tipos aquí sin modificar código existente.
 */
public enum TherapyType {
    BIOMAGNETIC("BIOMAGNETIC", "Biomagnetismo"),
    REFLEXOLOGY("REFLEXOLOGY", "Reflexología"),
    BIODECODIFICATION("BIODECODIFICATION", "Biodecodificación"),
    ACUPUNCTURE("ACUPUNCTURE", "Acupuntura"),
    HOLISTIC_THERAPY("HOLISTIC_THERAPY", "Terapias Holísticas");

    private final String code;
    private final String label;

    TherapyType(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String code() {
        return code;
    }

    public String label() {
        return label;
    }

    public static TherapyType fromCode(String code) {
        for (TherapyType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de terapia desconocido: " + code);
    }
}

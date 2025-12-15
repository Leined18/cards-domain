package com.health.tools.cards.domain.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record CardSummary(
                CardId id,
                CardType type,
                String displayName,
                String description,
                String imageUrl,
                Map<String, Object> details
) {
        public CardSummary {
                details = details == null ? Map.of() : sanitize(details);
        }

        private static Map<String, Object> sanitize(Map<String, Object> source) {
                Map<String, Object> copy = new LinkedHashMap<>();
                source.forEach((key, value) -> {
                        if (key != null && value != null) {
                                copy.put(key, value);
                        }
                });
                return Collections.unmodifiableMap(copy);
        }
}

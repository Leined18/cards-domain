package com.health.tools.cards.infrastructure.rest.dto;

import com.health.tools.cards.domain.model.CardType;
import java.util.Map;

public record CreateCardRequest(
    CardType type,
    String displayName,
    String description,
    String imageUrl,
    Map<String, Object> details
) {
}

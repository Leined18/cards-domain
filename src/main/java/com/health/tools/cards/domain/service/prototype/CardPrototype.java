package com.health.tools.cards.domain.service.prototype;

import com.health.tools.cards.domain.model.CardAggregate;
import com.health.tools.cards.domain.model.CardType;
import com.health.tools.cards.domain.model.details.CardDetails;

public interface CardPrototype {

    CardType supportedType();

    CardDetails createDetails();

    default void initialize(CardAggregate aggregate) {
        // Hook for prototypes that need to set default values.
    }
}

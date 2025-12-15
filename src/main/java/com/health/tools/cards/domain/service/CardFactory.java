package com.health.tools.cards.domain.service;

import com.health.tools.cards.domain.model.CardAggregate;
import com.health.tools.cards.domain.model.CardType;
import com.health.tools.cards.domain.model.details.CardDetails;
import com.health.tools.cards.domain.service.prototype.CardPrototype;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class CardFactory {

    private final Map<CardType, CardPrototype> registry = new EnumMap<>(CardType.class);

    public CardFactory(List<CardPrototype> prototypes) {
        Objects.requireNonNull(prototypes, "prototype list cannot be null");
        prototypes.stream()
                .filter(Objects::nonNull)
                .forEach(prototype -> registry.put(prototype.supportedType(), prototype));
        if (registry.isEmpty()) {
            throw new IllegalStateException("No card prototypes registered");
        }
    }

    public CardAggregate create(CardType type) {
        CardPrototype prototype = registry.get(type);
        if (prototype == null) {
            throw new IllegalArgumentException("No prototype registered for type " + type);
        }
        CardDetails details = prototype.createDetails();
        CardAggregate aggregate = new CardAggregate(type, details);
        prototype.initialize(aggregate);
        return aggregate;
    }
}

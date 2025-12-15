package com.health.tools.cards.domain.model.details;

import java.util.Map;

public interface CardDetails {

    void apply(Map<String, ?> values);

    Map<String, Object> asMap();
}

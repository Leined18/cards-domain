package com.health.tools.cards.domain.model;

import com.health.tools.cards.domain.model.details.CardDetails;

import java.util.Map;

public final class CardAggregate {

    private CardId id;
    private CardType type;
    private String displayName;
    private String description;
    private String imageUrl;
    private CardDetails details;

    public CardAggregate(CardType type, CardDetails details) {
        setType(type);
        setDetails(details);
    }

    public CardId getId() {
        return id;
    }

    public void setId(CardId id) {
        this.id = id;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        if (type == null) {
            throw new IllegalArgumentException("Card type cannot be null");
        }
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = sanitize(displayName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = sanitize(description);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = sanitize(imageUrl);
    }

    public CardDetails getDetails() {
        return details;
    }

    public void setDetails(CardDetails details) {
        if (details == null) {
            throw new IllegalArgumentException("Card details cannot be null");
        }
        this.details = details;
    }

    public void applyDetails(Map<String, ?> values) {
        details.apply(values);
    }

    public Map<String, Object> detailsAsMap() {
        return details.asMap();
    }

    public CardSummary toSummary() {
        return new CardSummary(
                id,
                type,
                displayName,
                description,
                imageUrl,
                details.asMap()
        );
    }

    private String sanitize(String value) {
        return value == null || value.isBlank() ? null : value.trim();
    }
}

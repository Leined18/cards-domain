package com.health.tools.cards.domain.model.details.biomagneticpair;

import com.health.tools.cards.domain.model.details.CardDetails;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class BiomagneticPairDetails implements CardDetails {

    private List<String> pairIds;
    private String classification;
    private Integer itemIndex;

    public List<String> getPairIds() {
        return pairIds;
    }

    public void setPairIds(List<String> pairIds) {
        this.pairIds = normalizePairIds(pairIds);
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = sanitize(classification);
    }

    public Integer getItemIndex() {
        return itemIndex;
    }

    public void setItemIndex(Integer itemIndex) {
        this.itemIndex = itemIndex;
    }

    @Override
    public void apply(Map<String, ?> values) {
        if (values == null || values.isEmpty()) {
            pairIds = null;
            classification = null;
            itemIndex = null;
            return;
        }
        if (values.containsKey("pairIds")) {
            setPairIds(asStringList(values.get("pairIds")));
        }
        if (values.containsKey("pairs")) {
            setPairIds(asStringList(values.get("pairs")));
        }
        if (values.containsKey("classification")) {
            setClassification(sanitize(values.get("classification")));
        }
        if (values.containsKey("itemIndex")) {
            setItemIndex(asInteger(values.get("itemIndex")));
        }
    }

    @Override
    public Map<String, Object> asMap() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("pairIds", pairIds == null ? null : List.copyOf(pairIds));
        result.put("classification", classification);
        result.put("itemIndex", itemIndex);
        return result;
    }

    private String sanitize(Object value) {
        if (value == null) {
            return null;
        }
        String text = value.toString().trim();
        return text.isEmpty() ? null : text;
    }

    private Integer asInteger(Object value) {
        if (value instanceof Number number) {
            return number.intValue();
        }
        if (value instanceof String text && !text.isBlank()) {
            try {
                return Integer.parseInt(text.trim());
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
        return null;
    }

    private List<String> asStringList(Object value) {
        if (value instanceof List<?> list) {
            List<String> normalized = list.stream()
                    .filter(Objects::nonNull)
                    .map(Object::toString)
                    .map(String::trim)
                    .filter(text -> !text.isEmpty())
                    .toList();
            return normalized.isEmpty() ? null : normalized;
        }
        if (value instanceof String text) {
            String trimmed = text.trim();
            return trimmed.isEmpty() ? null : List.of(trimmed);
        }
        return null;
    }

    private List<String> normalizePairIds(List<String> candidate) {
        if (candidate == null) {
            return null;
        }
        List<String> normalized = candidate.stream()
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(text -> !text.isEmpty())
                .toList();
        return normalized.isEmpty() ? null : normalized;
    }
}

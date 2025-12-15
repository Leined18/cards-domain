package com.health.tools.cards.infrastructure.persistence;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.tools.cards.domain.model.*;
import com.health.tools.cards.domain.model.details.CardDetails;
import com.health.tools.cards.domain.port.CardRepositoryPort;
import com.health.tools.cards.domain.service.CardFactory;
import com.health.tools.cards.infrastructure.persistence.entity.CardEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación del puerto de repositorio usando JPA.
 */
@Component
public class CardRepositoryAdapter implements CardRepositoryPort {
    
    private final CardJpaRepository jpaRepository;
    private final CardFactory cardFactory;
    private final ObjectMapper objectMapper;

    public CardRepositoryAdapter(CardJpaRepository jpaRepository, CardFactory cardFactory, ObjectMapper objectMapper) {
        this.jpaRepository = jpaRepository;
        this.cardFactory = cardFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void save(CardAggregate card) {
        CardEntity entity = toEntity(card);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<CardAggregate> findById(CardId id) {
        return jpaRepository.findById(id.value())
            .map(this::toDomain);
    }

    @Override
    public List<CardAggregate> findByType(CardType type) {
        return jpaRepository.findByCardType(type).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public List<CardAggregate> search(String query) {
        return jpaRepository.searchByQuery(query).stream()
            .map(this::toDomain)
            .toList();
    }

    @Override
    public void delete(CardId id) {
        jpaRepository.deleteById(id.value());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }

    // Mappers

    private CardEntity toEntity(CardAggregate card) {
        CardEntity entity = new CardEntity();
        entity.setId(card.getId().value());
        entity.setCardType(card.getType());
        entity.setDisplayName(card.getDisplayName());
        entity.setDescription(card.getDescription());
        entity.setImageUrl(card.getImageUrl());
        
        try {
            String detailsJson = objectMapper.writeValueAsString(card.detailsAsMap());
            entity.setDetailsJson(detailsJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando detalles de tarjeta", e);
        }
        
        return entity;
    }

    private CardAggregate toDomain(CardEntity entity) {
        CardAggregate card = cardFactory.create(entity.getCardType());
        card.setId(CardId.of(entity.getId()));
        card.setDisplayName(entity.getDisplayName());
        card.setDescription(entity.getDescription());
        card.setImageUrl(entity.getImageUrl());
        
        if (entity.getDetailsJson() != null) {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> detailsMap = objectMapper.readValue(entity.getDetailsJson(), Map.class);
                card.applyDetails(detailsMap);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error deserializando detalles de tarjeta", e);
            }
        }
        
        return card;
    }
}

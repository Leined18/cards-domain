package com.health.tools.cards.application.service;

import com.health.tools.cards.domain.model.*;
import com.health.tools.cards.domain.port.CardRepositoryPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio de aplicación para consultas (lectura) de tarjetas.
 * Gestiona la búsqueda y recuperación de tarjetas.
 */
@Service
@Transactional(readOnly = true)
public class CardQueryService {
    private final CardRepositoryPort repository;

    public CardQueryService(CardRepositoryPort repository) {
        this.repository = repository;
    }

    /**
     * Busca una tarjeta por ID.
     * 
     * @param id ID de la tarjeta
     * @return tarjeta si existe
     */
    public Optional<CardAggregate> findById(CardId id) {
        return repository.findById(id);
    }

    /**
     * Obtiene tarjetas por tipo.
     * 
     * @param type tipo de tarjeta
     * @return lista de tarjetas del tipo especificado
     */
    public List<CardAggregate> findByType(CardType type) {
        return repository.findByType(type);
    }

    /**
     * Busca tarjetas por término de búsqueda.
     * 
     * @param query término de búsqueda
     * @return lista de tarjetas que coinciden
     */
    public List<CardAggregate> searchCards(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        return repository.search(query.trim());
    }

    /**
     * Obtiene el total de tarjetas.
     * 
     * @return cantidad total
     */
    public long getTotalCount() {
        return repository.count();
    }

    /**
     * Obtiene resúmenes de tarjetas por tipo.
     * 
     * @param type tipo de tarjeta
     * @return lista de resúmenes
     */
    public List<CardSummary> getSummariesByType(CardType type) {
        return repository.findByType(type).stream()
            .map(CardAggregate::toSummary)
            .toList();
    }
}

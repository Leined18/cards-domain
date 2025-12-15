package com.health.tools.cards.infrastructure.rest;

import com.health.tools.cards.application.service.*;
import com.health.tools.cards.domain.model.*;
import com.health.tools.cards.infrastructure.rest.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * REST Controller para gestión de tarjetas de terapia.
 */
@RestController
@RequestMapping("/api/v1/cards")
@CrossOrigin(origins = "*")
public class CardController {
    
    private final CardService cardService;
    private final CardQueryService queryService;

    public CardController(CardService cardService, CardQueryService queryService) {
        this.cardService = cardService;
        this.queryService = queryService;
    }

    /**
     * Health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "cards-service",
            "timestamp", Instant.now().toString()
        ));
    }

    /**
     * Crea una nueva tarjeta.
     */
    @PostMapping
    public ResponseEntity<CardResponse> createCard(@RequestBody CreateCardRequest request) {
        CardAggregate card = cardService.createCard(
            request.type(),
            request.displayName(),
            request.description(),
            request.imageUrl(),
            request.details()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(card));
    }

    /**
     * Obtiene una tarjeta por ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCard(@PathVariable String id) {
        return queryService.findById(CardId.of(id))
            .map(card -> ResponseEntity.ok(toResponse(card)))
            .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista tarjetas con filtros opcionales.
     */
    @GetMapping
    public ResponseEntity<List<CardResponse>> listCards(
        @RequestParam(required = false) CardType type,
        @RequestParam(required = false) String search
    ) {
        List<CardAggregate> cards;
        
        if (search != null && !search.isBlank()) {
            cards = queryService.searchCards(search);
        } else if (type != null) {
            cards = queryService.findByType(type);
        } else {
            // Por defecto, retornar lista vacía o todas (implementar findAll si es necesario)
            cards = List.of();
        }
        
        List<CardResponse> response = cards.stream()
            .map(this::toResponse)
            .toList();
        
        return ResponseEntity.ok(response);
    }

    /**
     * Actualiza una tarjeta existente.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(
        @PathVariable String id,
        @RequestBody UpdateCardRequest request
    ) {
        try {
            CardAggregate card = cardService.updateCard(
                CardId.of(id),
                request.displayName(),
                request.description(),
                request.imageUrl(),
                request.details()
            );
            return ResponseEntity.ok(toResponse(card));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Elimina una tarjeta.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable String id) {
        cardService.deleteCard(CardId.of(id));
        return ResponseEntity.noContent().build();
    }

    /**
     * Obtiene el total de tarjetas.
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getCount() {
        long count = queryService.getTotalCount();
        return ResponseEntity.ok(Map.of("total", count));
    }

    // Mapper

    private CardResponse toResponse(CardAggregate card) {
        return new CardResponse(
            card.getId().value(),
            card.getType(),
            card.getDisplayName(),
            card.getDescription(),
            card.getImageUrl(),
            card.detailsAsMap()
        );
    }
}

package com.health.tools.cards.application.service;

import com.health.tools.cards.domain.model.*;
import com.health.tools.cards.domain.port.CardRepositoryPort;
import com.health.tools.cards.domain.service.CardFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * Servicio de aplicación para comandos (escritura) de tarjetas.
 * Gestiona la creación, actualización y eliminación de tarjetas.
 */
@Service
@Transactional
public class CardService {
    private final CardRepositoryPort repository;
    private final CardFactory cardFactory;

    public CardService(CardRepositoryPort repository, CardFactory cardFactory) {
        this.repository = repository;
        this.cardFactory = cardFactory;
    }

    /**
     * Crea una nueva tarjeta.
     * 
     * @param type tipo de tarjeta
     * @return tarjeta creada con ID generado
     */
    public CardAggregate createCard(CardType type) {
        CardAggregate card = cardFactory.create(type);
        card.setId(CardId.generate());
        repository.save(card);
        return card;
    }

    /**
     * Crea y configura una nueva tarjeta con detalles.
     * 
     * @param type tipo de tarjeta
     * @param displayName nombre para mostrar
     * @param description descripción
     * @param imageUrl URL de la imagen
     * @param details detalles específicos
     * @return tarjeta creada
     */
    public CardAggregate createCard(CardType type, String displayName, String description, 
                                     String imageUrl, Map<String, Object> details) {
        CardAggregate card = cardFactory.create(type);
        card.setId(CardId.generate());
        card.setDisplayName(displayName);
        card.setDescription(description);
        card.setImageUrl(imageUrl);
        if (details != null) {
            card.applyDetails(details);
        }
        repository.save(card);
        return card;
    }

    /**
     * Actualiza los datos de una tarjeta existente.
     * 
     * @param id ID de la tarjeta
     * @param displayName nuevo nombre
     * @param description nueva descripción
     * @param imageUrl nueva URL de imagen
     * @param details nuevos detalles
     * @return tarjeta actualizada
     */
    public CardAggregate updateCard(CardId id, String displayName, String description, 
                                     String imageUrl, Map<String, Object> details) {
        CardAggregate card = repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Card not found: " + id));
        
        card.setDisplayName(displayName);
        card.setDescription(description);
        card.setImageUrl(imageUrl);
        if (details != null) {
            card.applyDetails(details);
        }
        repository.save(card);
        return card;
    }

    /**
     * Elimina una tarjeta.
     * 
     * @param id ID de la tarjeta a eliminar
     */
    public void deleteCard(CardId id) {
        repository.delete(id);
    }
}

package com.health.tools.cards.domain.port;

import com.health.tools.cards.domain.model.CardAggregate;
import com.health.tools.cards.domain.model.CardId;
import com.health.tools.cards.domain.model.CardType;
import java.util.List;
import java.util.Optional;

/**
 * Puerto (Contrato) para acceder a las tarjetas de terapia en persistencia.
 * Implementa el patrón Ports & Adapters (Hexagonal Architecture).
 * 
 * Los adapters concretos (JPA, MongoDB, etc.) implementarán esta interfaz.
 */
public interface CardRepositoryPort {
    /**
     * Guarda una tarjeta en persistencia.
     * 
     * @param card tarjeta a guardar (puede ser nueva o actualizada)
     */
    void save(CardAggregate card);

    /**
     * Busca una tarjeta por ID.
     * 
     * @param id identificador de la tarjeta
     * @return tarjeta si existe
     */
    Optional<CardAggregate> findById(CardId id);

    /**
     * Obtiene todas las tarjetas de cierto tipo.
     * 
     * @param type tipo de tarjeta
     * @return lista de tarjetas del tipo especificado
     */
    List<CardAggregate> findByType(CardType type);

    /**
     * Busca tarjetas por término de búsqueda.
     * 
     * @param query término de búsqueda (busca en nombre, descripción, etc.)
     * @return lista de tarjetas que coinciden
     */
    List<CardAggregate> search(String query);

    /**
     * Elimina una tarjeta.
     * 
     * @param id identificador de la tarjeta a eliminar
     */
    void delete(CardId id);

    /**
     * Obtiene el total de tarjetas.
     * 
     * @return cantidad total
     */
    long count();
}

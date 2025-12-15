package com.health.tools.cards.infrastructure.persistence;

import com.health.tools.cards.infrastructure.persistence.entity.CardEntity;
import com.health.tools.cards.domain.model.CardType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para operaciones de persistencia de tarjetas.
 */
@Repository
public interface CardJpaRepository extends JpaRepository<CardEntity, String> {
    
    /**
     * Busca tarjetas por tipo.
     */
    List<CardEntity> findByCardType(CardType cardType);
    
    /**
     * Busca tarjetas por nombre (case insensitive).
     */
    @Query("SELECT c FROM CardEntity c WHERE LOWER(c.displayName) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(c.description) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<CardEntity> searchByQuery(@Param("query") String query);
}

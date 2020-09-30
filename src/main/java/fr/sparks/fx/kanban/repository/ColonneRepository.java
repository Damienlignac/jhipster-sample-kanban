package fr.sparks.fx.kanban.repository;

import fr.sparks.fx.kanban.domain.Colonne;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Colonne entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ColonneRepository extends JpaRepository<Colonne, Long> {
}

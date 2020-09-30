package fr.sparks.fx.kanban.repository;

import fr.sparks.fx.kanban.domain.TypeTache;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TypeTache entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TypeTacheRepository extends JpaRepository<TypeTache, Long> {
}

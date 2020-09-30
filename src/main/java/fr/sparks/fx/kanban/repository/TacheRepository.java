package fr.sparks.fx.kanban.repository;

import fr.sparks.fx.kanban.domain.Tache;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Tache entity.
 */
@Repository
public interface TacheRepository extends JpaRepository<Tache, Long> {

    @Query(value = "select distinct tache from Tache tache left join fetch tache.developpeurs",
        countQuery = "select count(distinct tache) from Tache tache")
    Page<Tache> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct tache from Tache tache left join fetch tache.developpeurs")
    List<Tache> findAllWithEagerRelationships();

    @Query("select tache from Tache tache left join fetch tache.developpeurs where tache.id =:id")
    Optional<Tache> findOneWithEagerRelationships(@Param("id") Long id);
}

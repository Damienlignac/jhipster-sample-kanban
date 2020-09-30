package fr.sparks.fx.kanban.domain;

import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The Employee entity.
 */
@ApiModel(description = "The Employee entity.")
@Entity
@Table(name = "colonne")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Colonne implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @OneToMany(mappedBy = "colonne")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Tache> taches = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Colonne nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public Colonne taches(Set<Tache> taches) {
        this.taches = taches;
        return this;
    }

    public Colonne addTache(Tache tache) {
        this.taches.add(tache);
        tache.setColonne(this);
        return this;
    }

    public Colonne removeTache(Tache tache) {
        this.taches.remove(tache);
        tache.setColonne(null);
        return this;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Colonne)) {
            return false;
        }
        return id != null && id.equals(((Colonne) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Colonne{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            "}";
    }
}

package fr.sparks.fx.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * Task entity.\n@author The JHipster team.
 */
@ApiModel(description = "Task entity.\n@author The JHipster team.")
@Entity
@Table(name = "tache")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tache implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "intitule")
    private String intitule;

    @Column(name = "dt_creation")
    private Instant dtCreation;

    @Column(name = "nb_heures_estimees")
    private Long nbHeuresEstimees;

    @Column(name = "nb_heures_reelles")
    private Long nbHeuresReelles;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "tache_developpeur",
               joinColumns = @JoinColumn(name = "tache_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "developpeur_id", referencedColumnName = "id"))
    private Set<Developpeur> developpeurs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "taches", allowSetters = true)
    private Colonne colonne;

    @ManyToOne
    @JsonIgnoreProperties(value = "taches", allowSetters = true)
    private TypeTache typeTache;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public Tache intitule(String intitule) {
        this.intitule = intitule;
        return this;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public Instant getDtCreation() {
        return dtCreation;
    }

    public Tache dtCreation(Instant dtCreation) {
        this.dtCreation = dtCreation;
        return this;
    }

    public void setDtCreation(Instant dtCreation) {
        this.dtCreation = dtCreation;
    }

    public Long getNbHeuresEstimees() {
        return nbHeuresEstimees;
    }

    public Tache nbHeuresEstimees(Long nbHeuresEstimees) {
        this.nbHeuresEstimees = nbHeuresEstimees;
        return this;
    }

    public void setNbHeuresEstimees(Long nbHeuresEstimees) {
        this.nbHeuresEstimees = nbHeuresEstimees;
    }

    public Long getNbHeuresReelles() {
        return nbHeuresReelles;
    }

    public Tache nbHeuresReelles(Long nbHeuresReelles) {
        this.nbHeuresReelles = nbHeuresReelles;
        return this;
    }

    public void setNbHeuresReelles(Long nbHeuresReelles) {
        this.nbHeuresReelles = nbHeuresReelles;
    }

    public Set<Developpeur> getDeveloppeurs() {
        return developpeurs;
    }

    public Tache developpeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
        return this;
    }

    public Tache addDeveloppeur(Developpeur developpeur) {
        this.developpeurs.add(developpeur);
        developpeur.getTaches().add(this);
        return this;
    }

    public Tache removeDeveloppeur(Developpeur developpeur) {
        this.developpeurs.remove(developpeur);
        developpeur.getTaches().remove(this);
        return this;
    }

    public void setDeveloppeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
    }

    public Colonne getColonne() {
        return colonne;
    }

    public Tache colonne(Colonne colonne) {
        this.colonne = colonne;
        return this;
    }

    public void setColonne(Colonne colonne) {
        this.colonne = colonne;
    }

    public TypeTache getTypeTache() {
        return typeTache;
    }

    public Tache typeTache(TypeTache typeTache) {
        this.typeTache = typeTache;
        return this;
    }

    public void setTypeTache(TypeTache typeTache) {
        this.typeTache = typeTache;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tache)) {
            return false;
        }
        return id != null && id.equals(((Tache) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tache{" +
            "id=" + getId() +
            ", intitule='" + getIntitule() + "'" +
            ", dtCreation='" + getDtCreation() + "'" +
            ", nbHeuresEstimees=" + getNbHeuresEstimees() +
            ", nbHeuresReelles=" + getNbHeuresReelles() +
            "}";
    }
}

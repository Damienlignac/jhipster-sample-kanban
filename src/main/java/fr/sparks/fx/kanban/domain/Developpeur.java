package fr.sparks.fx.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Developpeur.
 */
@Entity
@Table(name = "developpeur")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Developpeur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom")
    private String prenom;

    @Column(name = "dt_naissance")
    private Instant dtNaissance;

    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "nb_taches_en_cours")
    private Long nbTachesEnCours;

    @Column(name = "numero_carte_bleue")
    private String numeroCarteBleue;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "developpeur_projet",
               joinColumns = @JoinColumn(name = "developpeur_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "projet_id", referencedColumnName = "id"))
    private Set<Projet> projets = new HashSet<>();

    @ManyToMany(mappedBy = "developpeurs")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
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

    public Developpeur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public Developpeur prenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Instant getDtNaissance() {
        return dtNaissance;
    }

    public Developpeur dtNaissance(Instant dtNaissance) {
        this.dtNaissance = dtNaissance;
        return this;
    }

    public void setDtNaissance(Instant dtNaissance) {
        this.dtNaissance = dtNaissance;
    }

    public String getEmail() {
        return email;
    }

    public Developpeur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getNbTachesEnCours() {
        return nbTachesEnCours;
    }

    public Developpeur nbTachesEnCours(Long nbTachesEnCours) {
        this.nbTachesEnCours = nbTachesEnCours;
        return this;
    }

    public void setNbTachesEnCours(Long nbTachesEnCours) {
        this.nbTachesEnCours = nbTachesEnCours;
    }

    public String getNumeroCarteBleue() {
        return numeroCarteBleue;
    }

    public Developpeur numeroCarteBleue(String numeroCarteBleue) {
        this.numeroCarteBleue = numeroCarteBleue;
        return this;
    }

    public void setNumeroCarteBleue(String numeroCarteBleue) {
        this.numeroCarteBleue = numeroCarteBleue;
    }

    public Set<Projet> getProjets() {
        return projets;
    }

    public Developpeur projets(Set<Projet> projets) {
        this.projets = projets;
        return this;
    }

    public Developpeur addProjet(Projet projet) {
        this.projets.add(projet);
        projet.getDeveloppeurs().add(this);
        return this;
    }

    public Developpeur removeProjet(Projet projet) {
        this.projets.remove(projet);
        projet.getDeveloppeurs().remove(this);
        return this;
    }

    public void setProjets(Set<Projet> projets) {
        this.projets = projets;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public Developpeur taches(Set<Tache> taches) {
        this.taches = taches;
        return this;
    }

    public Developpeur addTache(Tache tache) {
        this.taches.add(tache);
        tache.getDeveloppeurs().add(this);
        return this;
    }

    public Developpeur removeTache(Tache tache) {
        this.taches.remove(tache);
        tache.getDeveloppeurs().remove(this);
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
        if (!(o instanceof Developpeur)) {
            return false;
        }
        return id != null && id.equals(((Developpeur) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Developpeur{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", prenom='" + getPrenom() + "'" +
            ", dtNaissance='" + getDtNaissance() + "'" +
            ", email='" + getEmail() + "'" +
            ", nbTachesEnCours=" + getNbTachesEnCours() +
            ", numeroCarteBleue='" + getNumeroCarteBleue() + "'" +
            "}";
    }
}

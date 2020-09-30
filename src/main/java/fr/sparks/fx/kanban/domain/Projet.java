package fr.sparks.fx.kanban.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * not an ignored comment
 */
@ApiModel(description = "not an ignored comment")
@Entity
@Table(name = "projet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "dt_naissance")
    private Instant dtNaissance;

    @Column(name = "dt_livraison")
    private Instant dtLivraison;

    @OneToMany(mappedBy = "projet")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Developpeur> developpeurs = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "projets", allowSetters = true)
    private Client client;

    @ManyToMany(mappedBy = "projets")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Developpeur> developpeurs = new HashSet<>();

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

    public Projet nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Instant getDtNaissance() {
        return dtNaissance;
    }

    public Projet dtNaissance(Instant dtNaissance) {
        this.dtNaissance = dtNaissance;
        return this;
    }

    public void setDtNaissance(Instant dtNaissance) {
        this.dtNaissance = dtNaissance;
    }

    public Instant getDtLivraison() {
        return dtLivraison;
    }

    public Projet dtLivraison(Instant dtLivraison) {
        this.dtLivraison = dtLivraison;
        return this;
    }

    public void setDtLivraison(Instant dtLivraison) {
        this.dtLivraison = dtLivraison;
    }

    public Set<Developpeur> getDeveloppeurs() {
        return developpeurs;
    }

    public Projet developpeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
        return this;
    }

    public Projet addDeveloppeur(Developpeur developpeur) {
        this.developpeurs.add(developpeur);
        developpeur.setProjet(this);
        return this;
    }

    public Projet removeDeveloppeur(Developpeur developpeur) {
        this.developpeurs.remove(developpeur);
        developpeur.setProjet(null);
        return this;
    }

    public void setDeveloppeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
    }

    public Client getClient() {
        return client;
    }

    public Projet client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Developpeur> getDeveloppeurs() {
        return developpeurs;
    }

    public Projet developpeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
        return this;
    }

    public Projet addDeveloppeur(Developpeur developpeur) {
        this.developpeurs.add(developpeur);
        developpeur.getProjets().add(this);
        return this;
    }

    public Projet removeDeveloppeur(Developpeur developpeur) {
        this.developpeurs.remove(developpeur);
        developpeur.getProjets().remove(this);
        return this;
    }

    public void setDeveloppeurs(Set<Developpeur> developpeurs) {
        this.developpeurs = developpeurs;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Projet)) {
            return false;
        }
        return id != null && id.equals(((Projet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Projet{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", dtNaissance='" + getDtNaissance() + "'" +
            ", dtLivraison='" + getDtLivraison() + "'" +
            "}";
    }
}

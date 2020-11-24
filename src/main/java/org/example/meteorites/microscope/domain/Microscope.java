package org.example.meteorites.microscope.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Microscope.
 */
@Entity
@Table(name = "microscope")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Microscope implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "microscope")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<MicroscopePicture> microscopePictures = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = "microscopes", allowSetters = true)
    private Rock rock;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Microscope name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<MicroscopePicture> getMicroscopePictures() {
        return microscopePictures;
    }

    public Microscope microscopePictures(Set<MicroscopePicture> microscopePictures) {
        this.microscopePictures = microscopePictures;
        return this;
    }

    public Microscope addMicroscopePicture(MicroscopePicture microscopePicture) {
        this.microscopePictures.add(microscopePicture);
        microscopePicture.setMicroscope(this);
        return this;
    }

    public Microscope removeMicroscopePicture(MicroscopePicture microscopePicture) {
        this.microscopePictures.remove(microscopePicture);
        microscopePicture.setMicroscope(null);
        return this;
    }

    public void setMicroscopePictures(Set<MicroscopePicture> microscopePictures) {
        this.microscopePictures = microscopePictures;
    }

    public Rock getRock() {
        return rock;
    }

    public Microscope rock(Rock rock) {
        this.rock = rock;
        return this;
    }

    public void setRock(Rock rock) {
        this.rock = rock;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Microscope)) {
            return false;
        }
        return id != null && id.equals(((Microscope) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Microscope{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}

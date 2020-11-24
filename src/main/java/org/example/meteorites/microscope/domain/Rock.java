package org.example.meteorites.microscope.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Rock.
 */
@Entity
@Table(name = "rock")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "picture_path")
    private String picturePath;

    @OneToMany(mappedBy = "rock")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Microscope> microscopes = new HashSet<>();

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

    public Rock name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public Rock desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public Rock picturePath(String picturePath) {
        this.picturePath = picturePath;
        return this;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public Set<Microscope> getMicroscopes() {
        return microscopes;
    }

    public Rock microscopes(Set<Microscope> microscopes) {
        this.microscopes = microscopes;
        return this;
    }

    public Rock addMicroscope(Microscope microscope) {
        this.microscopes.add(microscope);
        microscope.setRock(this);
        return this;
    }

    public Rock removeMicroscope(Microscope microscope) {
        this.microscopes.remove(microscope);
        microscope.setRock(null);
        return this;
    }

    public void setMicroscopes(Set<Microscope> microscopes) {
        this.microscopes = microscopes;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rock)) {
            return false;
        }
        return id != null && id.equals(((Rock) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rock{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", picturePath='" + getPicturePath() + "'" +
            "}";
    }
}

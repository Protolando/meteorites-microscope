package org.example.meteorites.microscope.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A MicroscopePicture.
 */
@Entity
@Table(name = "microscope_picture")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MicroscopePicture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "path")
    private String path;

    @Column(name = "name")
    private String name;

    @Column(name = "jhi_desc")
    private String desc;

    @Column(name = "jhi_order")
    private Integer order;

    @ManyToOne
    @JsonIgnoreProperties(value = "microscopePictures", allowSetters = true)
    private Microscope microscope;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public MicroscopePicture path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public MicroscopePicture name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public MicroscopePicture desc(String desc) {
        this.desc = desc;
        return this;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getOrder() {
        return order;
    }

    public MicroscopePicture order(Integer order) {
        this.order = order;
        return this;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Microscope getMicroscope() {
        return microscope;
    }

    public MicroscopePicture microscope(Microscope microscope) {
        this.microscope = microscope;
        return this;
    }

    public void setMicroscope(Microscope microscope) {
        this.microscope = microscope;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MicroscopePicture)) {
            return false;
        }
        return id != null && id.equals(((MicroscopePicture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MicroscopePicture{" +
            "id=" + getId() +
            ", path='" + getPath() + "'" +
            ", name='" + getName() + "'" +
            ", desc='" + getDesc() + "'" +
            ", order=" + getOrder() +
            "}";
    }
}

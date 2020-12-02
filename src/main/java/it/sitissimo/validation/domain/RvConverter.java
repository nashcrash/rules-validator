package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RvConverter.
 */
@Entity
@Table(name = "rv_converter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RvConverter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "converter_code", nullable = false, unique = true)
    private String converterCode;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "bean_name", nullable = false)
    private String beanName;

    @ManyToMany(mappedBy = "rvConverters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<RvParam> rvParams = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConverterCode() {
        return converterCode;
    }

    public RvConverter converterCode(String converterCode) {
        this.converterCode = converterCode;
        return this;
    }

    public void setConverterCode(String converterCode) {
        this.converterCode = converterCode;
    }

    public String getDescription() {
        return description;
    }

    public RvConverter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public RvConverter beanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Set<RvParam> getRvParams() {
        return rvParams;
    }

    public RvConverter rvParams(Set<RvParam> rvParams) {
        this.rvParams = rvParams;
        return this;
    }

    public RvConverter addRvParam(RvParam rvParam) {
        this.rvParams.add(rvParam);
        rvParam.getRvConverters().add(this);
        return this;
    }

    public RvConverter removeRvParam(RvParam rvParam) {
        this.rvParams.remove(rvParam);
        rvParam.getRvConverters().remove(this);
        return this;
    }

    public void setRvParams(Set<RvParam> rvParams) {
        this.rvParams = rvParams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvConverter)) {
            return false;
        }
        return id != null && id.equals(((RvConverter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvConverter{" +
            "id=" + getId() +
            ", converterCode='" + getConverterCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            "}";
    }
}

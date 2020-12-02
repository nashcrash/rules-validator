package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

import it.sitissimo.validation.domain.enumeration.RvParamType;

/**
 * A RvOperatorParam.
 */
@Entity
@Table(name = "rv_operator_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RvOperatorParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private RvParamType type;

    @ManyToOne
    @JsonIgnoreProperties(value = "params", allowSetters = true)
    private RvOperator operator;

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

    public RvOperatorParam name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public RvOperatorParam description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RvParamType getType() {
        return type;
    }

    public RvOperatorParam type(RvParamType type) {
        this.type = type;
        return this;
    }

    public void setType(RvParamType type) {
        this.type = type;
    }

    public RvOperator getOperator() {
        return operator;
    }

    public RvOperatorParam operator(RvOperator rvOperator) {
        this.operator = rvOperator;
        return this;
    }

    public void setOperator(RvOperator rvOperator) {
        this.operator = rvOperator;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvOperatorParam)) {
            return false;
        }
        return id != null && id.equals(((RvOperatorParam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvOperatorParam{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}

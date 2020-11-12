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
 * A Converter.
 */
@Entity
@Table(name = "converter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Converter implements Serializable {

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

    @ManyToMany(mappedBy = "converters")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Parameter> parameters = new HashSet<>();

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

    public Converter converterCode(String converterCode) {
        this.converterCode = converterCode;
        return this;
    }

    public void setConverterCode(String converterCode) {
        this.converterCode = converterCode;
    }

    public String getDescription() {
        return description;
    }

    public Converter description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public Converter beanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Set<Parameter> getParameters() {
        return parameters;
    }

    public Converter parameters(Set<Parameter> parameters) {
        this.parameters = parameters;
        return this;
    }

    public Converter addParameter(Parameter parameter) {
        this.parameters.add(parameter);
        parameter.getConverters().add(this);
        return this;
    }

    public Converter removeParameter(Parameter parameter) {
        this.parameters.remove(parameter);
        parameter.getConverters().remove(this);
        return this;
    }

    public void setParameters(Set<Parameter> parameters) {
        this.parameters = parameters;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Converter)) {
            return false;
        }
        return id != null && id.equals(((Converter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Converter{" +
            "id=" + getId() +
            ", converterCode='" + getConverterCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            "}";
    }
}

package it.sitissimo.validation.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Operator.
 */
@Entity
@Table(name = "operator")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Operator implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "operator_code", nullable = false, unique = true)
    private String operatorCode;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "bean_name", nullable = false)
    private String beanName;

    @Column(name = "number_of_params")
    private Integer numberOfParams;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public Operator operatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
        return this;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getDescription() {
        return description;
    }

    public Operator description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeanName() {
        return beanName;
    }

    public Operator beanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Integer getNumberOfParams() {
        return numberOfParams;
    }

    public Operator numberOfParams(Integer numberOfParams) {
        this.numberOfParams = numberOfParams;
        return this;
    }

    public void setNumberOfParams(Integer numberOfParams) {
        this.numberOfParams = numberOfParams;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Operator)) {
            return false;
        }
        return id != null && id.equals(((Operator) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Operator{" +
            "id=" + getId() +
            ", operatorCode='" + getOperatorCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", beanName='" + getBeanName() + "'" +
            ", numberOfParams=" + getNumberOfParams() +
            "}";
    }
}

package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.sitissimo.validation.domain.enumeration.RuleMode;

/**
 * A Rule.
 */
@Entity
@Table(name = "rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Rule implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "rule_code", nullable = false, unique = true)
    private String ruleCode;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private RuleMode mode;

    @ManyToOne
    @JsonIgnoreProperties(value = "rules", allowSetters = true)
    private Operator operator;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rule_param",
               joinColumns = @JoinColumn(name = "rule_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "param_id", referencedColumnName = "id"))
    private Set<Param> params = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public Rule ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDescription() {
        return description;
    }

    public Rule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RuleMode getMode() {
        return mode;
    }

    public Rule mode(RuleMode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(RuleMode mode) {
        this.mode = mode;
    }

    public Operator getOperator() {
        return operator;
    }

    public Rule operator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Set<Param> getParams() {
        return params;
    }

    public Rule params(Set<Param> params) {
        this.params = params;
        return this;
    }

    public Rule addParam(Param param) {
        this.params.add(param);
        param.getRules().add(this);
        return this;
    }

    public Rule removeParam(Param param) {
        this.params.remove(param);
        param.getRules().remove(this);
        return this;
    }

    public void setParams(Set<Param> params) {
        this.params = params;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Rule)) {
            return false;
        }
        return id != null && id.equals(((Rule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Rule{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", mode='" + getMode() + "'" +
            "}";
    }
}

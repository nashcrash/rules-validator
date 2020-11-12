package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import it.sitissimo.validation.domain.enumeration.RvRuleLevel;

import it.sitissimo.validation.domain.enumeration.RvRuleMode;

/**
 * A RvRule.
 */
@Entity
@Table(name = "rv_rule")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RvRule implements Serializable {

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
    @Column(name = "level", nullable = false)
    private RvRuleLevel level;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "mode", nullable = false)
    private RvRuleMode mode;

    @ManyToOne
    @JsonIgnoreProperties(value = "rvRules", allowSetters = true)
    private RvOperator operator;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rv_rule_rv_param",
               joinColumns = @JoinColumn(name = "rv_rule_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rv_param_id", referencedColumnName = "id"))
    private Set<RvParam> rvParams = new HashSet<>();

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

    public RvRule ruleCode(String ruleCode) {
        this.ruleCode = ruleCode;
        return this;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getDescription() {
        return description;
    }

    public RvRule description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RvRuleLevel getLevel() {
        return level;
    }

    public RvRule level(RvRuleLevel level) {
        this.level = level;
        return this;
    }

    public void setLevel(RvRuleLevel level) {
        this.level = level;
    }

    public RvRuleMode getMode() {
        return mode;
    }

    public RvRule mode(RvRuleMode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(RvRuleMode mode) {
        this.mode = mode;
    }

    public RvOperator getOperator() {
        return operator;
    }

    public RvRule operator(RvOperator rvOperator) {
        this.operator = rvOperator;
        return this;
    }

    public void setOperator(RvOperator rvOperator) {
        this.operator = rvOperator;
    }

    public Set<RvParam> getRvParams() {
        return rvParams;
    }

    public RvRule rvParams(Set<RvParam> rvParams) {
        this.rvParams = rvParams;
        return this;
    }

    public RvRule addRvParam(RvParam rvParam) {
        this.rvParams.add(rvParam);
        rvParam.getRvRules().add(this);
        return this;
    }

    public RvRule removeRvParam(RvParam rvParam) {
        this.rvParams.remove(rvParam);
        rvParam.getRvRules().remove(this);
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
        if (!(o instanceof RvRule)) {
            return false;
        }
        return id != null && id.equals(((RvRule) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvRule{" +
            "id=" + getId() +
            ", ruleCode='" + getRuleCode() + "'" +
            ", description='" + getDescription() + "'" +
            ", level='" + getLevel() + "'" +
            ", mode='" + getMode() + "'" +
            "}";
    }
}

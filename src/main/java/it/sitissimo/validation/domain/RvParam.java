package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A RvParam.
 */
@Entity
@Table(name = "rv_param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RvParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rv_param_rv_converter",
               joinColumns = @JoinColumn(name = "rv_param_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "rv_converter_id", referencedColumnName = "id"))
    private Set<RvConverter> rvConverters = new HashSet<>();

    @ManyToMany(mappedBy = "rvParams")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<RvRule> rvRules = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public RvParam value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<RvConverter> getRvConverters() {
        return rvConverters;
    }

    public RvParam rvConverters(Set<RvConverter> rvConverters) {
        this.rvConverters = rvConverters;
        return this;
    }

    public RvParam addRvConverter(RvConverter rvConverter) {
        this.rvConverters.add(rvConverter);
        rvConverter.getRvParams().add(this);
        return this;
    }

    public RvParam removeRvConverter(RvConverter rvConverter) {
        this.rvConverters.remove(rvConverter);
        rvConverter.getRvParams().remove(this);
        return this;
    }

    public void setRvConverters(Set<RvConverter> rvConverters) {
        this.rvConverters = rvConverters;
    }

    public Set<RvRule> getRvRules() {
        return rvRules;
    }

    public RvParam rvRules(Set<RvRule> rvRules) {
        this.rvRules = rvRules;
        return this;
    }

    public RvParam addRvRule(RvRule rvRule) {
        this.rvRules.add(rvRule);
        rvRule.getRvParams().add(this);
        return this;
    }

    public RvParam removeRvRule(RvRule rvRule) {
        this.rvRules.remove(rvRule);
        rvRule.getRvParams().remove(this);
        return this;
    }

    public void setRvRules(Set<RvRule> rvRules) {
        this.rvRules = rvRules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RvParam)) {
            return false;
        }
        return id != null && id.equals(((RvParam) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RvParam{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}

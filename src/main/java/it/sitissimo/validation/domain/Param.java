package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Param.
 */
@Entity
@Table(name = "param")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Param implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "param_converter",
               joinColumns = @JoinColumn(name = "param_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "converter_id", referencedColumnName = "id"))
    private Set<Converter> converters = new HashSet<>();

    @ManyToMany(mappedBy = "params")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnore
    private Set<Rule> rules = new HashSet<>();

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

    public Param value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Converter> getConverters() {
        return converters;
    }

    public Param converters(Set<Converter> converters) {
        this.converters = converters;
        return this;
    }

    public Param addConverter(Converter converter) {
        this.converters.add(converter);
        converter.getParams().add(this);
        return this;
    }

    public Param removeConverter(Converter converter) {
        this.converters.remove(converter);
        converter.getParams().remove(this);
        return this;
    }

    public void setConverters(Set<Converter> converters) {
        this.converters = converters;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Param rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public Param addRule(Rule rule) {
        this.rules.add(rule);
        rule.getParams().add(this);
        return this;
    }

    public Param removeRule(Rule rule) {
        this.rules.remove(rule);
        rule.getParams().remove(this);
        return this;
    }

    public void setRules(Set<Rule> rules) {
        this.rules = rules;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Param)) {
            return false;
        }
        return id != null && id.equals(((Param) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Param{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}

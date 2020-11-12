package it.sitissimo.validation.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Parameter.
 */
@Entity
@Table(name = "parameter")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "parameter_converter",
               joinColumns = @JoinColumn(name = "parameter_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "converter_id", referencedColumnName = "id"))
    private Set<Converter> converters = new HashSet<>();

    @ManyToMany(mappedBy = "parameters")
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

    public Parameter value(String value) {
        this.value = value;
        return this;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Set<Converter> getConverters() {
        return converters;
    }

    public Parameter converters(Set<Converter> converters) {
        this.converters = converters;
        return this;
    }

    public Parameter addConverter(Converter converter) {
        this.converters.add(converter);
        converter.getParameters().add(this);
        return this;
    }

    public Parameter removeConverter(Converter converter) {
        this.converters.remove(converter);
        converter.getParameters().remove(this);
        return this;
    }

    public void setConverters(Set<Converter> converters) {
        this.converters = converters;
    }

    public Set<Rule> getRules() {
        return rules;
    }

    public Parameter rules(Set<Rule> rules) {
        this.rules = rules;
        return this;
    }

    public Parameter addRule(Rule rule) {
        this.rules.add(rule);
        rule.getParameters().add(this);
        return this;
    }

    public Parameter removeRule(Rule rule) {
        this.rules.remove(rule);
        rule.getParameters().remove(this);
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
        if (!(o instanceof Parameter)) {
            return false;
        }
        return id != null && id.equals(((Parameter) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Parameter{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            "}";
    }
}

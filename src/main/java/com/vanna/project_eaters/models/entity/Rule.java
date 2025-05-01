package com.vanna.project_eaters.models.entity;

import com.vanna.project_eaters.models.enums.RuleType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Rule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Component component;

    @Enumerated(EnumType.STRING)
    private RuleType ruleType;

    private Double limitAmount; // может быть null, если просто запрещено

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rule rule = (Rule) o;

        Long thisComponentId = this.component != null ? this.component.getId() : null;
        Long otherComponentId = rule.component != null ? rule.component.getId() : null;

        if (thisComponentId != null ? !thisComponentId.equals(otherComponentId) : otherComponentId != null) return false;
        return ruleType == rule.ruleType;
    }

    @Override
    public int hashCode() {
        Long componentId = component != null ? component.getId() : null;
        int result = componentId != null ? componentId.hashCode() : 0;
        result = 31 * result + (ruleType != null ? ruleType.hashCode() : 0);
        return result;
    }

}



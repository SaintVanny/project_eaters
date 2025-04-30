package com.vanna.project_eaters.models.entity;

import com.vanna.project_eaters.models.enums.RuleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}


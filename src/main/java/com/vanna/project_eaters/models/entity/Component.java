package com.vanna.project_eaters.models.entity;

import com.vanna.project_eaters.models.enums.ComponentType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit; // г, мг и т. п.
    @Enumerated(EnumType.STRING)
    private ComponentType type;

}


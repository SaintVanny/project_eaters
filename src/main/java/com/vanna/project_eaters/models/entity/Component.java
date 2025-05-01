package com.vanna.project_eaters.models.entity;

import com.vanna.project_eaters.models.enums.ComponentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class Component {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String unit; // г, мг и т. п.

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ComponentType type;

}


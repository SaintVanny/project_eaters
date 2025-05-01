package com.vanna.project_eaters.models.entity;

import com.vanna.project_eaters.models.enums.Gender;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
public class UserParameters {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private double weight; // кг
    private double height; // см
    private int age;

}

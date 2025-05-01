package com.vanna.project_eaters.dto.dietDto;

import com.vanna.project_eaters.models.enums.ComponentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ComponentDto {
    private String name;
    private double amount;
    private ComponentType type;
}


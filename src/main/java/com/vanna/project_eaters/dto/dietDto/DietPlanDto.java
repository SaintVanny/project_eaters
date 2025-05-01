package com.vanna.project_eaters.dto.dietDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietPlanDto {
    private List<SelectedProductDto> selectedProducts;
    private double targetCalories;
}


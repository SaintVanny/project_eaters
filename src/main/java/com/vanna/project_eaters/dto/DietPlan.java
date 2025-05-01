package com.vanna.project_eaters.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DietPlan {
    private List<SelectedProduct> selectedProducts;
    private double totalCalories;
}

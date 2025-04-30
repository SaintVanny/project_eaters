package com.vanna.project_eaters.dto;

import com.vanna.project_eaters.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DietPlan {
    private List<Product> products;
    private double targetCalories;
}


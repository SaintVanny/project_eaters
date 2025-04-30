package com.vanna.project_eaters.dto;

import com.vanna.project_eaters.models.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectedProduct {
    private Product product;
    private int portionGrams;
}


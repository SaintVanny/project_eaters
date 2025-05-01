package com.vanna.project_eaters.dto.dietDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SelectedProductDto {
    private ProductDto product;
    private int portionGrams;
}


package com.vanna.project_eaters.dto.dietDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ProductDto {
    private String name;
    private List<ComponentDto> components;

}

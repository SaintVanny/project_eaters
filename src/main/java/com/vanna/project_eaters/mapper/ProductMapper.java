package com.vanna.project_eaters.mapper;

import com.vanna.project_eaters.dto.dietDto.ComponentDto;
import com.vanna.project_eaters.dto.dietDto.ProductDto;
import com.vanna.project_eaters.models.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductMapper {
    public static ProductDto toDto(Product product) {
        List<ComponentDto> components = product.getComponents().stream()
                .map(pc -> new ComponentDto(
                        pc.getComponent().getName(),
                        pc.getAmount(),
                        pc.getComponent().getType()
                ))
                .collect(Collectors.toList());

        return new ProductDto(product.getName(), components);
    }
}


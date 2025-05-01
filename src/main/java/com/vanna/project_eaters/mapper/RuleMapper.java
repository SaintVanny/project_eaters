package com.vanna.project_eaters.mapper;


import com.vanna.project_eaters.dto.dietDto.RuleDTO;
import com.vanna.project_eaters.models.entity.Component;
import com.vanna.project_eaters.models.entity.Rule;
import com.vanna.project_eaters.repository.ComponentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Component
@RequiredArgsConstructor
public class RuleMapper {

    private final ComponentRepository componentRepository;

    public Rule toEntity(RuleDTO dto) {
        Component component = componentRepository.findById(dto.getComponentId())
                .orElseThrow(() -> new IllegalArgumentException("Компонент с ID " + dto.getComponentId() + " не найден"));

        Rule rule = new Rule();
        rule.setRuleType(dto.getRuleType());
        rule.setLimitAmount(dto.getLimitAmount());
        rule.setComponent(component);
        return rule;
    }

    public List<Rule> toEntityList(List<RuleDTO> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}


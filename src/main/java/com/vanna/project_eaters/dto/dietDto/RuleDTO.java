package com.vanna.project_eaters.dto.dietDto;


import com.vanna.project_eaters.models.enums.RuleType;
import lombok.Data;

@Data
public class RuleDTO {
    private RuleType ruleType;
    private Double limitAmount;
    private Long componentId; // Только ID, без Component-объекта
}


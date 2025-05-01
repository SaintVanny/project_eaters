package com.vanna.project_eaters.dto.dietDto;

import com.vanna.project_eaters.models.entity.Rule;
import com.vanna.project_eaters.models.entity.UserParameters;
import lombok.Data;

import java.util.List;

@Data
public class DietRequest {
    private UserParameters userParameters;
    private List<Long> diseaseIds;
    private List<RuleDTO> customRules; // <-- индивидуальные правила, например без глютена
}

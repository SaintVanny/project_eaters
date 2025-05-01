package com.vanna.project_eaters.controllers;

import com.vanna.project_eaters.dto.dietDto.DietPlanDto;
import com.vanna.project_eaters.dto.dietDto.DietRequest;
import com.vanna.project_eaters.mapper.RuleMapper;
import com.vanna.project_eaters.models.entity.Disease;
import com.vanna.project_eaters.models.entity.authEntity.User;
import com.vanna.project_eaters.repository.ComponentRepository;
import com.vanna.project_eaters.repository.DiseaseRepository;
import com.vanna.project_eaters.service.DietCalculationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/diet")
@RequiredArgsConstructor
public class DietController {

    private final DietCalculationService dietCalculationService;
    private final DiseaseRepository diseaseRepository;
    private final ComponentRepository componentRepository;
    private final RuleMapper ruleMapper;


    @PostMapping
    public ResponseEntity<DietPlanDto> calculateDiet(@RequestBody DietRequest request,
                                                     @AuthenticationPrincipal User user) {
        System.out.println("Current user ID: " + user.getId());
        var userParameters = request.getUserParameters();
        List<Disease> diseases = diseaseRepository.findAllById(request.getDiseaseIds());

        diseases.forEach(disease -> System.out.println("Disease ID: " + disease.getId()));

        // добавим пользовательские правила вручную в список правил
        //TODO в CustomRules id = null надо исправить, пока json без custom rules
        if (request.getCustomRules() != null && !request.getCustomRules().isEmpty()) {
            Disease custom = new Disease(); // фиктивная болезнь с именем "custom"
            custom.setRules(ruleMapper.toEntityList(request.getCustomRules()));
            diseases.add(custom);
        }

        DietPlanDto dietPlanDto = dietCalculationService.calculateDiet(userParameters, diseases);
        return ResponseEntity.ok(dietPlanDto);
    }


}


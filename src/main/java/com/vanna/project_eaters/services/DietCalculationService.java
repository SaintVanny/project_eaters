package com.vanna.project_eaters.services;

import com.vanna.project_eaters.dto.DietPlan;
import com.vanna.project_eaters.models.entity.Disease;
import com.vanna.project_eaters.models.entity.Product;
import com.vanna.project_eaters.models.entity.Rule;
import com.vanna.project_eaters.models.entity.UserParameters;
import com.vanna.project_eaters.models.enums.Gender;
import com.vanna.project_eaters.models.enums.RuleType;
import com.vanna.project_eaters.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietCalculationService {

    private final ProductRepository productRepository;

    /**
     * Главный метод: получает список продуктов, болезни пользователя и параметры
     */
    public DietPlan calculateDiet(UserParameters parameters, List<Disease> diseases) {
        double dailyCalories = calculateCalories(parameters);
        List<Rule> rules = collectRules(diseases);

        List<Product> allProducts = productRepository.findAll();

        List<Product> allowedProducts = filterProducts(allProducts, rules);

        // На этом этапе можно построить рацион
        return buildDietPlan(allowedProducts, dailyCalories, rules);
    }

    /**
     * Подсчет базовой нормы калорий (формула Миффлина — Сан Жеора)
     */
    private double calculateCalories(UserParameters p) {
        if (p.getGender() == Gender.MALE) {
            return 10 * p.getWeight() + 6.25 * p.getHeight() - 5 * p.getAge() + 5;
        } else {
            return 10 * p.getWeight() + 6.25 * p.getHeight() - 5 * p.getAge() - 161;
        }
    }

    /**
     * Сбор всех уникальных правил
     */
    private List<Rule> collectRules(List<Disease> diseases) {
        return diseases.stream()
                .flatMap(d -> d.getRules().stream())
                .distinct()
                .collect(Collectors.toList());
    }

    /**
     * Исключает продукты, содержащие запрещённые компоненты
     */
    private List<Product> filterProducts(List<Product> products, List<Rule> rules) {
        Set<Long> forbiddenComponentIds = rules.stream()
                .filter(rule -> rule.getRuleType() == RuleType.FORBIDDEN)
                .map(rule -> rule.getComponent().getId())
                .collect(Collectors.toSet());

        return products.stream()
                .filter(product -> product.getComponents().stream()
                        .noneMatch(pc -> forbiddenComponentIds.contains(pc.getComponent().getId())))
                .collect(Collectors.toList());
    }

    /**
     * Заглушка — подбор продуктов, суммарно покрывающих нужную калорийность
     */
    private DietPlan buildDietPlan(List<Product> products, double targetCalories, List<Rule> rules) {
        // Реализация: отбор подходящих продуктов, суммирование компонентов, учёт ограничений
        return new DietPlan(products, targetCalories);
    }
}


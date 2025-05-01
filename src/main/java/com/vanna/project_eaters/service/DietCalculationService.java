package com.vanna.project_eaters.service;

import com.vanna.project_eaters.dto.DietPlan;
import com.vanna.project_eaters.dto.dietDto.DietPlanDto;
import com.vanna.project_eaters.dto.SelectedProduct;
import com.vanna.project_eaters.dto.dietDto.SelectedProductDto;
import com.vanna.project_eaters.mapper.ProductMapper;
import com.vanna.project_eaters.models.entity.*;
import com.vanna.project_eaters.models.enums.ComponentType;
import com.vanna.project_eaters.models.enums.Gender;
import com.vanna.project_eaters.models.enums.RuleType;
import com.vanna.project_eaters.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.google.common.util.concurrent.AtomicDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DietCalculationService {

    private final ProductRepository productRepository;

    /**
     * Главный метод: получает список продуктов, болезни пользователя и параметры
     */
    public DietPlanDto calculateDiet(UserParameters parameters, List<Disease> diseases) {
        double dailyCalories = calculateCalories(parameters);
        List<Rule> rules = collectRules(diseases);

        List<Product> allProducts = productRepository.findAll();
        List<Product> allowedProducts = filterProducts(allProducts, rules);

        DietPlan plan = buildDietPlan(allowedProducts, dailyCalories, rules); // <-- внутренний план

        List<SelectedProductDto> dtoList = plan.getSelectedProducts().stream()
                .map(sp -> new SelectedProductDto(
                        ProductMapper.toDto(sp.getProduct()),
                        sp.getPortionGrams()))
                .collect(Collectors.toList());

        return new DietPlanDto(dtoList, plan.getTotalCalories());
    }

    /**
     * Подсчет базовой нормы калорий (формула Миффлина — Сан Жеора)
     */
    private double calculateCalories(UserParameters p) {
        if (p.getGender() == Gender.MALE) {
            return (10 * p.getWeight()) + (6.25 * p.getHeight()) - (5 * p.getAge()) + 5;
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
        // Ограничения по компонентам
        Map<Long, Double> limitedComponents = rules.stream()
                .filter(r -> r.getRuleType() == RuleType.LIMITED)
                .collect(Collectors.toMap(
                        r -> r.getComponent().getId(),
                        Rule::getLimitAmount
                ));

        Set<Long> recommendedComponentIds = rules.stream()
                .filter(r -> r.getRuleType() == RuleType.RECOMMENDED)
                .map(r -> r.getComponent().getId())
                .collect(Collectors.toSet());

        List<Product> recommendedProducts = new ArrayList<>();
        List<Product> otherProducts = new ArrayList<>();

        for (Product product : products) {
            boolean hasRecommended = product.getComponents().stream()
                    .anyMatch(pc -> recommendedComponentIds.contains(pc.getComponent().getId()));

            if (hasRecommended) {
                recommendedProducts.add(product);
            } else {
                otherProducts.add(product);
            }
        }

        List<SelectedProduct> selected = new ArrayList<>();
        AtomicDouble totalCalories = new AtomicDouble(0.0);

        fillDiet(recommendedProducts, selected, limitedComponents, targetCalories, totalCalories);

        if (totalCalories.get() < targetCalories) {
            fillDiet(otherProducts, selected, limitedComponents, targetCalories, totalCalories);
        }

        return new DietPlan(selected, totalCalories.get());
    }

    private double calculateCalories(Product product) {
        return product.getComponents().stream()
                .mapToDouble(pc -> {
                    ComponentType type = pc.getComponent().getType();
                    double amount = pc.getAmount(); // в 100 г

                    return switch (type) {
                        case PROTEIN -> amount * 4.0;
                        case CARBOHYDRATE -> amount * 4.0;
                        case FAT -> amount * 9.0;
                        default -> 0.0;
                    };
                }).sum();
    }

    private boolean respectsLimits(Product product, Map<Long, Double> limits) {
        for (ProductComponent pc : product.getComponents()) {
            Double limit = limits.get(pc.getComponent().getId());
            if (limit != null && pc.getAmount() > limit) {
                return false;
            }
        }
        return true;
    }

    private void fillDiet(
            List<Product> source,
            List<SelectedProduct> selected,
            Map<Long, Double> limits,
            double targetCalories,
            AtomicDouble totalCalories
    ) {
        for (Product product : source) {
            double productCalories = calculateCalories(product);
            if (productCalories <= 0) continue;
            if (!respectsLimits(product, limits)) continue;

            selected.add(new SelectedProduct(product, 100));
            totalCalories.addAndGet(productCalories);

            if (totalCalories.get() >= targetCalories) break;
        }
    }



}


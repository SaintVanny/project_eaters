package com.vanna.project_eaters.repositories;

import com.vanna.project_eaters.models.entity.Disease;
import com.vanna.project_eaters.models.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
}

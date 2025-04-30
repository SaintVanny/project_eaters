package com.vanna.project_eaters.repositories;

import com.vanna.project_eaters.models.entity.UserParameters;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserParametersRepository extends JpaRepository<UserParameters, Long> {
}


package com.vanna.project_eaters.repository;

import com.vanna.project_eaters.models.entity.UserParameters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserParametersRepository extends JpaRepository<UserParameters, Long> {
}


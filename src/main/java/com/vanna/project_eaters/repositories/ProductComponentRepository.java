package com.vanna.project_eaters.repositories;

import com.vanna.project_eaters.models.entity.ProductComponent;
import com.vanna.project_eaters.models.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductComponentRepository extends JpaRepository<ProductComponent, Long> {
    List<ProductComponent> findByProductId(Long productId);
}

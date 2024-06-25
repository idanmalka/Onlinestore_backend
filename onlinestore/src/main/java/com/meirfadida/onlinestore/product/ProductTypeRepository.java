package com.meirfadida.onlinestore.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p.type AS productType FROM Product p WHERE p.id = :id")
    ProductType findProductTypeById(@Param("id") Long id);
}

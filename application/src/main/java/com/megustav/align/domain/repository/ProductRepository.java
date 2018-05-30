package com.megustav.align.domain.repository;

import com.megustav.align.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Product repository
 *
 * @author MeGustav
 * 30/05/2018 21:19
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Find all products by name
     *
     * @param name product name
     * @return found products
     */
    List<Product> findAllByName(String name);

    /**
     * Find product by his name and brand
     *
     * @param name product name
     * @param brand brand name
     * @return optional product
     */
    Optional<Product> findByNameAndBrandName(String name, String brand);

}

package com.megustav.align.domain.repository;

import com.megustav.align.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    /**
     * Find products whose quantities are less than needed
     *
     * @param quantity quantity upper bound
     * @param pageable pagination
     * @return leftovers
     */
    Page<Product> findAllByQuantityLessThanEqual(int quantity, Pageable pageable);

    /**
     * Get product page
     *
     * @param name product name
     * @param brand brand
     * @param pageable {@link Pageable} configuration
     * @return page
     */
    Page<Product> findByNameContainingAndBrand_NameContaining(String name, String brand, Pageable pageable);
    
}

package com.megustav.align.repository;

import com.megustav.align.repository.entity.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Products repository
 *
 * @author MeGustav
 * 27/05/2018 23:56
 */
public interface ProductRepository {

    /**
     * Get all products from DB
     *
     * @return all products information
     */
    List<Product> getAllProducts();

    /**
     * Get all brands from DB
     *
     * @return all available brands
     */
    List<String> getAllBrands();

    /**
     * Find product by name
     *
     * @param name product name
     * @return product
     */
    List<Product> findProducts(String name);

    /**
     * Find product by name
     *
     * @param name product name
     * @return product
     */
    Optional<Product> findProduct(String name, String brand);

    /**
     * Insert product
     *
     * @param name product name
     * @param price product price
     * @param price quantity
     * @return whether or not product was inserted
     */
    boolean insertProduct(String name, long brandId, BigDecimal price, long quantity);

    /**
     * Insert brand
     *
     * @param brand brand name
     * @return whether or not brand was inserted
     */
    boolean insertBrand(String brand);

}

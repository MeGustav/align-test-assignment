package com.megustav.align.repository;

import com.megustav.align.repository.entity.Product;

import java.util.List;

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

}

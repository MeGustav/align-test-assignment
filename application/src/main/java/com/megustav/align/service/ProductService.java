package com.megustav.align.service;

import com.megustav.align.service.entity.Product;

import java.util.List;

/**
 * Service for products operations
 *
 * @author MeGustav
 * 28/05/2018 01:21
 */
public interface ProductService {

    /**
     * Get all products as DTOs
     *
     * @return all products
     */
    List<Product> getAllProducts();

}

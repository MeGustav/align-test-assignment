package com.megustav.align.service.impl;

import com.megustav.align.repository.ProductRepository;
import com.megustav.align.service.ProductService;
import com.megustav.align.service.entity.ProductDTO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for products operations
 *
 * @author MeGustav
 * 28/05/2018 01:29
 */
public class ProductServiceImpl implements ProductService {

    /** Products DAO */
    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repository.getAllProducts().stream()
                .map(entity -> new ProductDTO(
                        entity.getName(),
                        entity.getBrand(),
                        entity.getPrice(),
                        entity.getQuantity())
                ).collect(Collectors.toList());
    }
}

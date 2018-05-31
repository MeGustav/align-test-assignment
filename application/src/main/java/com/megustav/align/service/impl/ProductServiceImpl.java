package com.megustav.align.service.impl;

import com.megustav.align.Application;
import com.megustav.align.domain.entity.Product;
import com.megustav.align.domain.repository.ProductRepository;
import com.megustav.align.service.ProductService;
import com.megustav.align.service.entity.ProductDTO;
import com.megustav.align.service.entity.ProductsResponse;
import com.megustav.align.service.entity.filter.ProductsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for products operations
 *
 * @author MeGustav
 * 28/05/2018 01:29
 */
public class ProductServiceImpl implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public ProductsResponse getProducts(ProductsFilter filter) {
        log.debug("Processing filter: {}", filter);
        ProductsFilter.Sort sort = filter.getSort();
        ProductsFilter.Pagination pagination = filter.getPagination();
        ProductsFilter.Search search = filter.getSearch();

        Pageable pageable = createPageable(pagination, sort);

        Page<Product> page = search == null ?
                repository.findAll(pageable) :
                repository.findByNameContainingAndBrand_NameContaining(
                        prepareSearchField(search.getName()),
                        prepareSearchField(search.getBrand()),
                        pageable
                );
        log.debug("Got {} products", page.getTotalElements());
        log.trace("Products: {}", page.getContent());
        return new ProductsResponse(page.getTotalElements(), mapProducts(page.getContent()));
    }

    /**
     * Search fields can't be null for JPA.
     * So swapping it wit an empty string.
     *
     * At the moment have not time for an investigation
     * regarding the overhead of this implementation
     * (3 separate methods of getting products for all 3
     * remaining cases could be better)
     *
     * @param field field
     * @return tweaked field
     */
    private String prepareSearchField(String field) {
        return field == null ? "" : field;
    }

    /**
     * Create {@link Pageable} instance to request products
     *
     * @param pagination pagination filter
     * @param sort sort filter
     * @return {@link Pageable}
     */
    private Pageable createPageable(ProductsFilter.Pagination pagination, ProductsFilter.Sort sort) {
        if (sort == null) {
            return PageRequest.of(pagination.getPage(), pagination.getSize());
        }
        Sort.Order order = new Sort.Order(
                sort.isAsc() ? Sort.Direction.ASC : Sort.Direction.DESC,
                sort.getField()
        );
        return PageRequest.of(pagination.getPage(), pagination.getSize(), Sort.by(order));
    }

    /**
     * Map list of {@link Product} to list of {@link ProductDTO}
     *
     * @param entities list of {@link Product}
     * @return list of {@link ProductDTO}
     */
    private List<ProductDTO> mapProducts(List<Product> entities) {
        return entities.stream()
                .map(entity -> new ProductDTO(
                        entity.getName(),
                        entity.getBrand().getName(),
                        entity.getPrice(),
                        entity.getQuantity()
                ))
                .collect(Collectors.toList());
    }
}

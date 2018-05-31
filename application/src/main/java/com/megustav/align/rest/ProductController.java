package com.megustav.align.rest;

import com.megustav.align.Application;
import com.megustav.align.service.ProductService;
import com.megustav.align.service.entity.ProductsResponse;
import com.megustav.align.service.entity.filter.ProductsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Product REST controller
 *
 * @author MeGustav
 * 27/05/2018 22:29
 */
@RestController
@RequestMapping(path = "products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    /**
     * Get products applying filter
     *
     * @param filter filter
     * @return products response
     */
    @RequestMapping(
            method = RequestMethod.POST,
            path = "filter",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductsResponse getProducts(@RequestBody ProductsFilter filter) {
        log.debug("Got request with filter: {}", filter);
        return service.getProducts(filter);
    }

}

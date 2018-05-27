package com.megustav.align.configuration;

import com.megustav.align.rest.ProductController;
import com.megustav.align.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * REST controller configuration
 *
 * @author MeGustav
 * 28/05/2018 01:20
 */
@Configuration
@Import({ ServiceConfiguration.class })
public class ControllerConfiguration {

    /** Product service */
    private final ProductService productService;

    @Autowired
    public ControllerConfiguration(ProductService productService) {
        this.productService = productService;
    }

    /**
     * @return controller bean
     */
    @Bean
    public ProductController productController() {
        return new ProductController(productService);
    }

}

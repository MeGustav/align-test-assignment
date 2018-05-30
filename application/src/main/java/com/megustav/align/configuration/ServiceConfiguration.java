package com.megustav.align.configuration;

import com.megustav.align.domain.repository.ProductRepository;
import com.megustav.align.service.ProductService;
import com.megustav.align.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Services configuration
 *
 * @author MeGustav
 * 28/05/2018 01:34
 */
@Configuration
@Import({ JpaConfiguration.class })
public class ServiceConfiguration {

    /** Products repository */
    private final ProductRepository productRepository;

    @Autowired
    public ServiceConfiguration(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * @return product service
     */
    @Bean
    public ProductService productService() {
        return new ProductServiceImpl(productRepository);
    }

}

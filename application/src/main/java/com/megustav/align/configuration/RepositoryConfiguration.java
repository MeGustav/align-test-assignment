package com.megustav.align.configuration;

import com.megustav.align.repository.ProductRepository;
import com.megustav.align.repository.impl.ProductRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Repositories configuration
 *
 * @author MeGustav
 * 28/05/2018 00:24
 */
@Configuration
@Import({ DataSourceConfiguration.class })
public class RepositoryConfiguration {

    /** JDBC template */
    private final JdbcTemplate template;

    @Autowired
    public RepositoryConfiguration(JdbcTemplate template) {
        this.template = template;
    }

    /**
     * @return products repository
     */
    @Bean
    public ProductRepository productsRepository() {
        return new ProductRepositoryImpl(template);
    }
}

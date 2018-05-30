package com.megustav.align.configuration;

import com.megustav.align.domain.entity.Brand;
import com.megustav.align.domain.entity.Product;
import com.megustav.align.domain.repository.BrandRepository;
import com.megustav.align.domain.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * JPA specific beans configuration
 *
 * @author MeGustav
 * 30/05/2018 21:21
 */
@Configuration
public class JpaConfiguration {

    private final DataSource dataSource;

    @Autowired
    public JpaConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.megustav.align.domain.entity");
        factory.setDataSource(dataSource);
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory());
        return txManager;
    }

    /**
     * Create brands repository.
     *
     * Creating this way instead of {@link org.springframework.data.jpa.repository.config.EnableJpaRepositories}
     * to make everything configured from {@link Configuration} files
     *
     * @return brands repository
     */
    @Bean
    public JpaRepositoryFactoryBean<BrandRepository, Brand, Long> brandRepository() {
        return new JpaRepositoryFactoryBean<>(BrandRepository.class);
    }

    /**
     * Create products repository.
     *
     * Creating this way instead of {@link org.springframework.data.jpa.repository.config.EnableJpaRepositories}
     * to make everything configured from {@link Configuration} files
     *
     * @return products repository
     */
    @Bean
    public JpaRepositoryFactoryBean<ProductRepository, Product, Long> productRepository() {
        return new JpaRepositoryFactoryBean<>(ProductRepository.class);
    }

}

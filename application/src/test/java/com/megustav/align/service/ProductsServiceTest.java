package com.megustav.align.service;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.megustav.align.configuration.ServiceConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.service.entity.ProductDTO;
import com.megustav.align.service.entity.ProductsResponse;
import com.megustav.align.service.entity.filter.ProductsFilter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * Product service tests.
 *
 * Using DBUnit.
 * It requires usage of listeners interfering with other annotations,
 * so should be used with caution
 *
 * @author MeGustav
 * 27/05/2018 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        ServiceConfiguration.class
})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
public class ProductsServiceTest {

    @Autowired
    private ProductService productService;

    /**
     * Test successfully getting products
     */
    @Test
    @DatabaseSetup("/data/db/setup/simple-content.xml")
    public void getProductsSimple() {
        // Just pagination
        ProductsFilter filter = new ProductsFilter(
                new ProductsFilter.Pagination(0, 50), null, null
        );
        ProductsResponse products =
                productService.getProducts(filter);
        Assertions.assertThat(products.getTotalSize()).isEqualTo(4);
        Assertions.assertThat(products.getPageProducts())
                .hasSize(4)
                .extracting(ProductDTO::getName).containsAnyOf("Glasses", "Sneakers");
    }

    /**
     * Test successfully getting products
     */
    @Test
    @DatabaseSetup("/data/db/setup/simple-content.xml")
    public void getProductsSearch() {
        // Pagination with search
        ProductsFilter filter = new ProductsFilter(
                new ProductsFilter.Pagination(0, 50), null, new ProductsFilter.Search("S", "")
        );
        ProductsResponse products =
                productService.getProducts(filter);
        Assertions.assertThat(products.getTotalSize()).isEqualTo(2);
        Assertions.assertThat(products.getPageProducts())
                .hasSize(2)
                .extracting(ProductDTO::getName).containsExactlyInAnyOrder("Shirt", "Sneakers");
    }
}

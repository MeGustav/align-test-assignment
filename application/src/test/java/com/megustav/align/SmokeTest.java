package com.megustav.align;

import com.megustav.align.configuration.ControllerConfiguration;
import com.megustav.align.configuration.RepositoryConfiguration;
import com.megustav.align.configuration.ServiceConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.repository.ProductRepository;
import com.megustav.align.rest.ProductController;
import com.megustav.align.service.ProductService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Smoke tests
 *
 * @author MeGustav
 * 27/05/2018 22:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        RepositoryConfiguration.class,
        ServiceConfiguration.class,
        ControllerConfiguration.class
})
public class SmokeTest {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ProductService service;

    @Autowired
    private ProductController controller;

    @Test
    public void mainComponentsShouldBeInitialized() {
        Assertions.assertThat(repository).as("Repository").isNotNull();
        Assertions.assertThat(service).as("Service").isNotNull();
        Assertions.assertThat(controller).as("Controller").isNotNull();
    }

}

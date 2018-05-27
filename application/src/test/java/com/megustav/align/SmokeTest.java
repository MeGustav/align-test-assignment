package com.megustav.align;

import com.megustav.align.configuration.ControllerConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import com.megustav.align.rest.ProductController;
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
        ControllerConfiguration.class
})
public class SmokeTest {

    @Autowired
    private ProductController controller;

    /**
     * Test controller bean present
     */
    @Test
    public void controllerShouldBeInitialized() {
        Assertions.assertThat(controller).isNotNull();
    }

}

package com.megustav.align;

import com.megustav.align.configuration.ControllerConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Product controller tests
 *
 * @author MeGustav
 * 27/05/2018 23:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@EnableWebMvc
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        ControllerConfiguration.class
})
@WithMockUser
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * Test successfully getting products
     */
    @Test
    public void testGetSuccess() throws Exception {
        mvc.perform(
                MockMvcRequestBuilders.get("/products").accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }
}

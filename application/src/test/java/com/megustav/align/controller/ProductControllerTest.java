package com.megustav.align.controller;

import com.megustav.align.configuration.ControllerConfiguration;
import com.megustav.align.configuration.TestDataSourceConfiguration;
import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
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

import java.nio.charset.StandardCharsets;

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
@WithMockUser
@ContextConfiguration(classes = {
        TestDataSourceConfiguration.class,
        ControllerConfiguration.class
})
public class ProductControllerTest {

    /** Object used to invoke requests */
    @Autowired
    private MockMvc mvc;

    /**
     * Test successfully getting products
     */
    @Test
    @Ignore("Getting 403 instead of 200, investigate")
    public void testGetSuccess() throws Exception {
        String payload = IOUtils.toString(
                getClass().getResourceAsStream("/data/payload/filter/only-pagination.json"),
                StandardCharsets.UTF_8
        );
        mvc.perform(
                MockMvcRequestBuilders.post("/products/filter")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }
}

package com.megustav.align.configuration;

import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

/**
 * Test datasource configuration
 *
 * @author MeGustav
 * 27/05/2018 23:33
 */
@TestComponent
@Configuration
public class TestDataSourceConfiguration {

    /**
     * @return {@link DataSource} instance
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                // У каждого контекста своя БД
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }

    /**
     * @return {@link JdbcTemplate} form DB manipulations
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

}

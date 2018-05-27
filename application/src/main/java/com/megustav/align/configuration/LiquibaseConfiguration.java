package com.megustav.align.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Liquibase configuration
 *
 * @author MeGustav
 * 27/05/2018 23:41
 */
@Configuration
@Import({ DataSourceConfiguration.class })
public class LiquibaseConfiguration {

    /** JDBC template */
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public LiquibaseConfiguration(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * @return liquibase runner
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(jdbcTemplate.getDataSource());
        liquibase.setChangeLog("classpath:migration/changelog-master.xml");
        return liquibase;
    }

}

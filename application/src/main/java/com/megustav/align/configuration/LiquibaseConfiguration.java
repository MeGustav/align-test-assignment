package com.megustav.align.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Liquibase configuration
 *
 * @author MeGustav
 * 27/05/2018 23:41
 */
@Configuration
public class LiquibaseConfiguration {

    private final DataSource dataSource;

    @Autowired
    public LiquibaseConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * @return liquibase runner
     */
    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:migration/changelog-master.xml");
        return liquibase;
    }

}

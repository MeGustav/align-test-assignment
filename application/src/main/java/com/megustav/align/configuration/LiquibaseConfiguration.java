package com.megustav.align.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Liquibase configuration
 *
 * @author MeGustav
 * 27/05/2018 23:41
 */
@Configuration
@Import({ DataSourceConfiguration.class })
public class LiquibaseConfiguration {

    private final Environment environment;
    private final DataSource dataSource;

    @Autowired
    public LiquibaseConfiguration(Environment environment, DataSource dataSource) {
        this.environment = environment;
        this.dataSource = dataSource;
    }

    /**
     * @return liquibase runner
     */
    @Bean("liquibase")
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setDefaultSchema(environment.getProperty("datasource.schema"));
        liquibase.setChangeLog("classpath:migration/changelog-master.xml");
        return liquibase;
    }

}

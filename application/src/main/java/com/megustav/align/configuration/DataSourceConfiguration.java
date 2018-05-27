package com.megustav.align.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * Datasource specific beans configuration
 *
 * @author MeGustav
 * 27/05/2018 23:24
 */
@Configuration
public class DataSourceConfiguration {

    /** Environment parameters */
    private final Environment environment;

    @Autowired
    public DataSourceConfiguration(Environment environment) {
        this.environment = environment;
    }

    /**
     * @return {@link JdbcTemplate} form DB manipulations
     */
    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    /**
     * @return {@link DataSource} instance
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(
                environment.getRequiredProperty("datasource.url"),
                environment.getRequiredProperty("datasource.username"),
                environment.getRequiredProperty("datasource.password"));
        dataSource.setSchema(environment.getProperty("datasource.schema"));

        HikariConfig poolConfig = new HikariConfig();
        poolConfig.setDataSource(dataSource);

        long connectionTimeout = environment.getProperty(
                "datasource.pool.connection-timeout", Long.class, 30_000L);
        poolConfig.setConnectionTimeout(connectionTimeout);
        poolConfig.setValidationTimeout(connectionTimeout);
        poolConfig.setPoolName("db-connection-pool");
        poolConfig.setMaximumPoolSize(environment.getProperty(
                "datasource.pool.max-size", Integer.class, 20));
        poolConfig.setMinimumIdle(environment.getProperty(
                "datasource.pool.min-idle", Integer.class, 10));

        return new HikariDataSource(poolConfig);
    }

}

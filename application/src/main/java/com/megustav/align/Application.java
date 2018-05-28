package com.megustav.align;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * Application entry point
 *
 * @author MeGustav
 * 27/05/2018 21:44
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = {
        HibernateJpaAutoConfiguration.class,
        JpaRepositoriesAutoConfiguration.class
})
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        log.info("Starting application...");
        new SpringApplicationBuilder(Application.class)
                .bannerMode(Banner.Mode.OFF)
                .listeners(new ApplicationPidFileWriter("application.pid"))
                .run(args);
        log.info("App started");

    }
}

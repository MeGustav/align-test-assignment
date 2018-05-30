package com.megustav.align;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

/**
 * Application entry point
 *
 * @author MeGustav
 * 27/05/2018 21:44
 */
@SpringBootApplication
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

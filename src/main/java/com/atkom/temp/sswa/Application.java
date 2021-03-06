package com.atkom.temp.sswa;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    private final static Logger logger = LogManager.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.run(args);
        logRun(args);
    }

    private static void logRun(String[] args) {
        logger.info("After SpringApplication.run()");
        for (String arg : args) {
            logger.info(args[0]);
        }
    }

}

package org.tester;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class Router {

    private static final Logger logger = LoggerFactory.getLogger(Router.class);

    @Autowired
    Environment environment;

    public static void main(String[] args) {
        SpringApplication.run(Router.class, args);
    }

    @PostConstruct
    public void load() {
        logger.info("Base Url Loaded: {}", environment.getProperty("base.url"));
    }
}
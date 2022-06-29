package org.tester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.tester.service.RequestService;
import org.tester.service.impl.RequestServiceImpl;
import org.tester.service.impl.RequestServiceImplWithLogs;

@SpringBootApplication
public class Router {
    @Autowired
    Environment environment;

    static boolean enableRequestLogs;

    public static void main(String[] args) {
        SpringApplication.run(Router.class, args);
    }

    @Bean
    public RequestService loadRequestService(ApplicationContext context) {
        RequestService requestService;
        enableRequestLogs = Boolean.parseBoolean(environment.getProperty("enable.request.logs"));

        requestService = enableRequestLogs ?
                context.getBean(RequestServiceImplWithLogs.class) :
                context.getBean(RequestServiceImpl.class);

        return requestService;
    }

}
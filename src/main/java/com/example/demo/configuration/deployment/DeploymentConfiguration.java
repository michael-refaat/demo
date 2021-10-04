package com.example.demo.configuration.deployment;

import com.example.demo.DemoApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DeploymentConfiguration extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return createSpringApplicationBuilder().sources(DemoApplication.class);
    }
}

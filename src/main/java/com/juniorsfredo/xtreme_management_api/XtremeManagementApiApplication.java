package com.juniorsfredo.xtreme_management_api;

import com.juniorsfredo.xtreme_management_api.infrastructure.config.dotenv.DotenvApplicationContextInitializer;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class XtremeManagementApiApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(XtremeManagementApiApplication.class);
        app.addInitializers(new DotenvApplicationContextInitializer());
        app.run(args);
    }
}

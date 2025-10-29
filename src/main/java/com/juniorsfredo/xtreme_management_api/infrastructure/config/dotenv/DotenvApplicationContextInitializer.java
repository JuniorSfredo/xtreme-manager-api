package com.juniorsfredo.xtreme_management_api.infrastructure.config.dotenv;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class DotenvApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {

        String activeProfile = System.getProperty("spring.profiles.active");
        if (activeProfile == null) {
            String[] profiles = applicationContext.getEnvironment().getActiveProfiles();
            if (profiles.length > 0) {
                activeProfile = profiles[0];
            }
        }

        String envFile = ".env";
        if ("test".equals(activeProfile)) {
            envFile = ".env.test";
        }

        System.out.println("🔹 Dotenv carregando arquivo: " + envFile);

        Dotenv dotenv = Dotenv.configure()
                .filename(envFile)
                .ignoreIfMalformed()
                .ignoreIfMissing()
                .load();

        for (DotenvEntry entry : dotenv.entries()) {
            System.setProperty(entry.getKey(), entry.getValue());
            System.out.println("✅ VAR: " + entry.getKey() + "=" + entry.getValue());
        }
    }
}

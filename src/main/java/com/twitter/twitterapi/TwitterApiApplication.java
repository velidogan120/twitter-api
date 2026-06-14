package com.twitter.twitterapi;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TwitterApiApplication {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUsername;

    @Value("${spring.datasource.password}")
    private String datasourcePassword;

    public static void main(String[] args) {
        SpringApplication.run(TwitterApiApplication.class, args);
    }

    @Bean
    public CommandLineRunner runFlyway() {
        return args -> {
            System.out.println("=== FLYWAY MANUEL OLARAK BAŞLATILIYOR ===");
            try {
                Flyway flyway = Flyway.configure()
                        .dataSource(datasourceUrl, datasourceUsername, datasourcePassword)
                        .baselineOnMigrate(true)
                        .locations("classpath:db/migration")
                        .load();

                flyway.migrate();
                System.out.println("=== FLYWAY BAŞARIYLA TAMAMLANDI ===");
            } catch (Exception e) {
                System.out.println("=== FLYWAY HATA ALDI: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

}

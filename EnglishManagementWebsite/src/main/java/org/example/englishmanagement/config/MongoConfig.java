package org.example.englishmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "org.example.englishmanagement.repository")
public class MongoConfig {
}

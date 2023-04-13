package com.noor.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


@Configuration
public class MongoConfiguration {
    @Bean
    public MongoClient mongodb() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/recommender");
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();
        return MongoClients.create(mongoClientSettings);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        try {
            return new MongoTemplate(mongodb(), "recommender");
        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return this.mongoTemplate();
    }
}
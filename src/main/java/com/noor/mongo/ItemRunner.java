package com.noor.mongo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class ItemRunner implements CommandLineRunner {
    @Override
    public void run(String... args){
        try {
            Item item=new Item();
            this.mongoTemplate.save(item);
            System.out.println("Application started");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
    private MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }
}

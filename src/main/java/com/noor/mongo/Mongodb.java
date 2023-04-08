package com.noor.configurations;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;

import javax.swing.text.Document;

import static com.mongodb.assertions.Assertions.assertNotNull;

public class Mongodb {
    private static final String HOST = "localhost";
    private static final String PORT = "27017";
    private static final String DB = "recomender";
    private static final String USER = "";
    private static final String PASS = "";

    private void assertInsertSucceeds(ConfigurableApplicationContext context) {
        String name = "A";

        MongoTemplate mongo = context.getBean(MongoTemplate.class);
        Document doc = ("{\"name\":\"" + name + "\"}");
        Document inserted = mongo.insert(doc, "items");

        assertNotNull(inserted.get("_id"));
        assertEquals(inserted.get("name"), name);
    }

}

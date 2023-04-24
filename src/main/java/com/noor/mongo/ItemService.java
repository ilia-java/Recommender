package com.noor.mongo;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;

@Service
public class ItemService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Item addItem(Item item) {
        return mongoTemplate.insert(item);
    }

    public boolean deleteItem(String id) {
        Item item = getItem(id);
        DeleteResult remove = mongoTemplate.remove(item);
        return remove.wasAcknowledged();
    }

    public Item getItem(String id) {
        return mongoTemplate.findById(id, Item.class);
    }

}

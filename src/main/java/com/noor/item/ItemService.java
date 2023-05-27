package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


import java.util.List;


@Service
public class ItemService {
    private final MongoTemplate mongoTemplate;
    private final ItemRepository itemRepository;


    public ItemService(MongoTemplate mongoTemplate, ItemRepository itemRepository) {
        this.mongoTemplate = mongoTemplate;
        this.itemRepository = itemRepository;
    }

        public Item getItemById(int dataBaseId) {
            return itemRepository.findById(dataBaseId).orElse(null);
        }

        public List<Item> getAllItems() {
            return itemRepository.findAll();
        }

        public Item createItem(Item item) {
            return itemRepository.save(item);
        }

        public Item updateItem(int dataBaseId, Item item) {
            Item existingItem = itemRepository.findById(dataBaseId).orElse(null);
            if (existingItem == null) {
                return null;
            } else {
                existingItem.setName(item.getName());
                existingItem.setColsName(item.getColsName());
                existingItem.setItemId(item.getItemId());
                return itemRepository.save(existingItem);
            }
        }

        public void deleteItem(int dataBaseId) {
            itemRepository.deleteById(dataBaseId);
        }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
package com.noor.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {
    private final MongoTemplate mongoTemplate;
    private final ItemRepository itemRepository;
    private final Logger logger = LoggerFactory.getLogger(ItemService.class);

    public ItemService(MongoTemplate mongoTemplate, ItemRepository itemRepository) {
        this.mongoTemplate = mongoTemplate;
        this.itemRepository = itemRepository;
    }

    public Item getItemById(int dataBaseId) {
        logger.info("Getting item with id: {}", dataBaseId);
        return itemRepository.findById(dataBaseId).orElse(null);
    }

    public List<Item> getAllItems() {
        logger.info("Getting all items");
        return itemRepository.findAll();
    }

    public Item createItem(Item item) {
        logger.info("Creating new item: {}", item.getName());
        return itemRepository.save(item);
    }

    public Item updateItem(int dataBaseId, Item item) {
        logger.info("Updating item with id: {}", dataBaseId);
        Item existingItem = itemRepository.findById(dataBaseId).orElse(null);
        if (existingItem == null) {
            logger.warn("Item with id {} does not exist", dataBaseId);
            return null;
        } else {
            existingItem.setName(item.getName());
            existingItem.setColsName(item.getColsName());
            existingItem.setItemId(item.getItemId());
            return itemRepository.save(existingItem);
        }
    }

    public void deleteItem(int dataBaseId) {
        logger.info("Deleting item with id: {}", dataBaseId);
        itemRepository.deleteById(dataBaseId);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
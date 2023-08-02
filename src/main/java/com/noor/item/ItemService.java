package com.noor.item;

import com.mongodb.MongoException;
import com.noor.GeneralExceptionHandling.ConnectionException;
import com.noor.GeneralExceptionHandling.NotFoundException;
import com.noor.GeneralExceptionHandling.InvalidInputException;
import com.noor.interactions.Interactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<Item> getItem(List<String> itemIdList, List<String> databaseIdList) {
        List<Item> items = null;
        try {
            ProjectionOperation projection = Aggregation.project("databaseId", "itemId", "name", "colsName");
            MatchOperation match = Aggregation.match(new Criteria().andOperator(
                    databaseIdList != null && !databaseIdList.isEmpty() ? Criteria.where("databaseId").in(databaseIdList) : new Criteria(),
                    itemIdList != null && !itemIdList.isEmpty() ? Criteria.where("itemId").in(itemIdList) : new Criteria()));
            Aggregation aggregation = Aggregation.newAggregation(match, projection);
            items = mongoTemplate.aggregate(aggregation, "item", Item.class).getMappedResults();
        } catch (DataAccessException | MongoException ex) {
            throw new ConnectionException("Error while accessing database: " + ex.getMessage(), ex);
        } finally {
            if (items == null || items.isEmpty()) {
                throw new NotFoundException("No item found with the given parameters: idList=" + ", itemIdList=" + itemIdList + ", databaseIdList=" + databaseIdList);
            }
        }
        return items;
    }


    public Item add(Item item) {
        try {
            if (item == null) {
                throw new InvalidInputException("Item cannot be null");
            }
            if (item.getItemId() == null || item.getItemId().isEmpty()) {
                throw new InvalidInputException("Item ID cannot be empty");
            }
            if (item.getDatabaseId() == null || item.getDatabaseId().isEmpty()) {
                throw new InvalidInputException("Database ID cannot be empty");
            }
            return itemRepository.save(item);
        } catch (DuplicateKeyException ex) {
            throw new ConnectionException("This is a duplicate ID", ex);
        } catch (DataAccessException ex) {
            throw new ConnectionException("Error while accessing database", ex);
        } catch (InvalidInputException ex) {
            throw new InvalidInputException("", ex);
        } catch (Exception ex) {
            throw new ConnectionException("Unexpected error occurred", ex);
        }
    }

    public List<Item> updateItem(List<Item> newItems) {
        List<Item> updatedItem = new ArrayList<>();
        for (Item newItem : newItems) {
            try {
                Query query = new Query();
                query.addCriteria(Criteria.where("itemId").is(newItem.getItemId())
                        .and("databaseId").is(newItem.getDatabaseId()));
                Update update = new Update();
                update.set("databaseId", newItem.getDatabaseId());
                update.set("itemId", newItem.getItemId());
                update.set("name", newItem.getName());
                update.set("colsName", newItem.getColsName());
                mongoTemplate.updateFirst(query, update, Item.class);
                updatedItem.add(newItem);

            } catch (NotFoundException ex) {
                throw new NotFoundException("Item  " + newItem + " does not exist");

            } catch (DataAccessException | MongoException ex) {
                logger.error("Error while updating item: {}", ex.getMessage());
                throw new ConnectionException("Error while accessing database", ex);

            } catch (IllegalArgumentException ex) {
                logger.error("Invalid input: {}", ex.getMessage());
                throw new InvalidInputException("Invalid input: " + ex.getMessage(), ex);

            } catch (NullPointerException ex) {
                logger.error("Missing required parameters: {}", ex.getMessage());
                throw new InvalidInputException("Missing required parameters: " + ex.getMessage(), ex);

            } catch (Exception ex) {
                logger.error("Unexpected error: {}", ex.getMessage());
                throw new RuntimeException("Unexpected error occurred", ex);
            }
        }
        return updatedItem;
    }

    public void deleteItem(Item item) {
        try {
            Criteria criteria = Criteria.where("itemId").is(item.getItemId())
                    .and("databaseId").is(item.getDatabaseId());
            Query query = Query.query(criteria);
            mongoTemplate.remove(query, Item.class);
        } catch (DataAccessException ex) {
            throw new ConnectionException("Failed to delete item", ex);
        }
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
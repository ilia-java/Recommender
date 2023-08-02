package com.noor.interactions;

import com.mongodb.MongoException;
import com.noor.GeneralExceptionHandling.ConnectionException;
import com.noor.GeneralExceptionHandling.InvalidInputException;
import com.noor.GeneralExceptionHandling.NotFoundException;
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
public class InteractionsService {
    private final MongoTemplate mongoTemplate;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    private final Logger logger = LoggerFactory.getLogger(InteractionsService.class);
    private final InteractionsRepository interactionsRepository;

    public InteractionsService(MongoTemplate mongoTemplate, InteractionsRepository interactionsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.interactionsRepository = interactionsRepository;
    }

    public List<Interactions> getInteractions( List<String> itemIdList, List<String> userIdList, List<String> databaseIdList) {
        List<Interactions> interactions = null;
        try {
            ProjectionOperation projection = Aggregation.project("_id", "itemId", "userId", "databaseId", "rating", "timeStamp");
            MatchOperation match = Aggregation.match(new Criteria().andOperator(
                    itemIdList != null && !itemIdList.isEmpty() ? Criteria.where("itemId").in(itemIdList) : new Criteria(),
                    userIdList != null && !userIdList.isEmpty() ? Criteria.where("userId").in(userIdList) : new Criteria(),
                    databaseIdList != null && !databaseIdList.isEmpty() ? Criteria.where("databaseId").in(databaseIdList) : new Criteria()
            ));
            Aggregation aggregation = Aggregation.newAggregation(match, projection);
            interactions = mongoTemplate.aggregate(aggregation, "interactions", Interactions.class).getMappedResults();
        } catch (DataAccessException | MongoException ex) {
            logger.error("Error while accessing database: {}", ex.getMessage(), ex);
            throw new ConnectionException("Error while accessing database: " + ex.getMessage(), ex);
        } finally {
            if (interactions == null || interactions.isEmpty()) {
                throw new NotFoundException("No interactions found with the given parameters: idList=" +  ", itemIdList=" + itemIdList + ", userIdList=" + userIdList + ", databaseIdList=" + databaseIdList);
            }
        }
        return interactions;
}
    public Interactions add(Interactions interactions) {
        try {
            if (interactions == null) {
                throw new InvalidInputException("Item cannot be null");
            }
            if (interactions.getItemId() == null || interactions.getItemId().isEmpty()) {
                throw new InvalidInputException("Item ID cannot be empty");
            }
            if (interactions.getUserId() == null || interactions.getUserId().isEmpty()) {
                throw new InvalidInputException("User ID cannot be empty");
            }
            if (interactions.getDatabaseId() == null || interactions.getDatabaseId().isEmpty()) {
                throw new InvalidInputException("Database ID cannot be empty");
            }
            return interactionsRepository.save(interactions);
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

    public List<Interactions> updateInteractions(List<Interactions> newInteractions) {
        List<Interactions> updatedInteractions = new ArrayList<>();
        for (Interactions newInteraction : newInteractions) {
            try {
                Query query = new Query();
                query.addCriteria(Criteria.where("itemId").is(newInteraction.getItemId())
                        .and("userId").is(newInteraction.getUserId())
                        .and("databaseId").is(newInteraction.getDatabaseId()));
                Update update = new Update();
                update.set("itemId", newInteraction.getItemId());
                update.set("userId", newInteraction.getUserId());
                update.set("databaseId", newInteraction.getDatabaseId());
                update.set("rating", newInteraction.getRating());
                update.set("timeStamp", newInteraction.getTimeStamp());
                mongoTemplate.updateFirst(query, update, Interactions.class);
                updatedInteractions.add(newInteraction);

            } catch (NotFoundException ex) {
                throw new NotFoundException("Interactions  " + newInteraction + " does not exist");

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
        return updatedInteractions;
    }

    public void deleteInteractions(Interactions interactions) {
        try {
            Criteria criteria = Criteria.where("itemId").is(interactions.getItemId())
                    .and("userId").is(interactions.getUserId())
                    .and("databaseId").is(interactions.getDatabaseId());
            Query query = Query.query(criteria);
            mongoTemplate.remove(query, Interactions.class);
        } catch (DataAccessException ex) {
            throw new ConnectionException("Failed to delete interactions", ex);
        }
    }
}

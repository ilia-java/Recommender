package com.noor.recommendedItems;

import com.mongodb.MongoException;
import com.noor.GeneralExceptionHandling.ConnectionException;
import com.noor.GeneralExceptionHandling.NotFoundException;
import com.noor.GeneralExceptionHandling.InvalidInputException;
import com.noor.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
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
import java.util.Optional;

@Service
public class RecommendedItemsService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendedItemsService.class);
    private final MongoTemplate mongoTemplate;

    private final RecommendedItemsRepository recommendedItemsRepository;

    public RecommendedItemsService(MongoTemplate mongoTemplate, RecommendedItemsRepository recommendedItemsRepository) {
        this.mongoTemplate = mongoTemplate;
        this.recommendedItemsRepository = recommendedItemsRepository;
    }

    public List<RecommendedItems> getRecommendedItem( List<String> userIdList, List<String> databaseIdList,  List<Integer> countList) {
        List<RecommendedItems> recommendedItems = null;
        try {
            ProjectionOperation projection = Aggregation.project( "userId","databaseId", "count", "cascadeCreate", "scenario");
            MatchOperation match = Aggregation.match(new Criteria().andOperator(
                    userIdList != null && !userIdList.isEmpty() ? Criteria.where("userId").in(userIdList) : new Criteria(),
                    databaseIdList != null && !databaseIdList.isEmpty() ? Criteria.where("databaseId").in(databaseIdList) : new Criteria(),
                    countList != null && !countList.isEmpty() ? Criteria.where("count").in(countList) : new Criteria()));
            Aggregation aggregation = Aggregation.newAggregation(match, projection);
            recommendedItems = mongoTemplate.aggregate(aggregation, "recommendedItems", RecommendedItems.class).getMappedResults();
        } catch (DataAccessException | MongoException ex) {
            logger.error("Error while accessing database: {}", ex.getMessage(), ex);
            throw new ConnectionException("Error while accessing database: " + ex.getMessage(), ex);
        } finally {
            if (recommendedItems == null || recommendedItems.isEmpty()) {
                throw new NotFoundException("No user found with this userId : "+ userIdList +"and databaseId"+databaseIdList);
            }
        }
        return recommendedItems;
    }

    public RecommendedItems addRecommendedItems(RecommendedItems recommendedItems) {
        try {
            logger.info("Adding new recommended item with id: {}", recommendedItems.getDatabaseId());
            if (recommendedItems == null) {
                throw new InvalidInputException("RecommendedItems cannot be null");
            }
            if (recommendedItems.getDatabaseId() == null || recommendedItems.getDatabaseId().isEmpty()) {
                throw new InvalidInputException("Recommended item database id cannot be empty");
            }
            return recommendedItemsRepository.save(recommendedItems);
        } catch (DataAccessException ex) {
            logger.error("Error while accessing database: {}", ex.getMessage());
            throw new ConnectionException("Error while accessing database", ex);
        } catch (InvalidInputException ex) {
            logger.error("Invalid input: {}", ex.getMessage());
            throw ex;
        } catch (com.mongodb.DuplicateKeyException ex) {
            logger.error("This is a duplicate ID: {}", ex.getMessage());
            throw new ConnectionException("This is a duplicate ID: " + ex.getMessage(), ex);
        } catch (Exception ex) {
            logger.error("Unexpected error: {}", ex.getMessage());
            throw new RuntimeException("Unexpected error occurred", ex);
        }
    }

    public List<RecommendedItems> updateRecommendedItems(List<RecommendedItems> recommendedItems) {
        List<RecommendedItems> updateRecommendedItem = new ArrayList<>();
        for (RecommendedItems newRecommendedItems : recommendedItems) {
            try {
                Query query = new Query();
                query.addCriteria(Criteria.where("userId").is(newRecommendedItems.getUserId())
                        .and("count").is(newRecommendedItems.getCount())
                        .and("databaseId").is(newRecommendedItems.getDatabaseId()));
                Update update = new Update();
                update.set("userId", newRecommendedItems.getUserId());
                update.set("databaseId", newRecommendedItems.getDatabaseId());
                update.set("count",newRecommendedItems.getCount());
                update.set("cascadeCreate",newRecommendedItems.getCascadeCreate());
                update.set("scenario",newRecommendedItems.getScenario());
                mongoTemplate.updateFirst(query, update, RecommendedItems.class);
                updateRecommendedItem.add(newRecommendedItems);

            } catch (NotFoundException ex) {
                throw new NotFoundException("RecommendedItems with userId  " + newRecommendedItems + " does not exist");

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
        return updateRecommendedItem;
    }

    public void deleteRecommendedItems(RecommendedItems recommendedItems) {
        try {
            Criteria criteria = Criteria.where("userId").is(recommendedItems.getUserId())
                    .and("databaseId").is(recommendedItems.getDatabaseId())
                    .and("count").is(recommendedItems.getCount());
            Query query = Query.query(criteria);
            mongoTemplate.remove(query, RecommendedItems.class);
        } catch (DataAccessException ex) {
            throw new ConnectionException("Failed to delete interactions", ex);
        }
}

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
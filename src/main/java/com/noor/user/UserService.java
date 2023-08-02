package com.noor.user;

import com.mongodb.MongoException;
import com.noor.GeneralExceptionHandling.ConnectionException;
import com.noor.GeneralExceptionHandling.InvalidInputException;
import com.noor.GeneralExceptionHandling.NotFoundException;
import com.noor.interactions.Interactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
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
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public UserService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }
    public List<User> getUser(List<String> userIdList, List<String> databaseIdList) {
        List<User> user = null;
        try {
            ProjectionOperation projection = Aggregation.project( "userId", "databaseId", "colsName", "password", "userName");
            MatchOperation match = Aggregation.match(new Criteria().andOperator(
                    userIdList != null ? Criteria.where("userId").in(userIdList) : new Criteria(),
                    databaseIdList != null ? Criteria.where("databaseId").in(databaseIdList) : new Criteria()
            ));
            Aggregation aggregation = Aggregation.newAggregation(match, projection);
            user = mongoTemplate.aggregate(aggregation, "user", User.class).getMappedResults();
        } catch (DataAccessException | MongoException ex) {
            throw new ConnectionException("Error while accessing database: " + ex.getMessage(), ex);
        } finally {
            if (user == null || user.isEmpty()) {
                throw new NotFoundException("No user found with userId: " + userIdList + " and databaseId: " + databaseIdList);
            }
        }
        return user;
    }



    public User add(User user) {
        try {
            if (user == null) {
                throw new InvalidInputException("Item cannot be null");
            }
            if (user.getUserId() == null || user.getUserId().isEmpty()) {
                throw new InvalidInputException("User ID cannot be empty");
            }
            if (user.getDatabaseId() == null || user.getDatabaseId().isEmpty()) {
                throw new InvalidInputException("Database ID cannot be empty");
            }
            return userRepository.save(user);
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
    public List<User> updateUsers(List<User> users) {
        List<User> updateUser = new ArrayList<>();
        for (User user : users) {
            try {
                Query query = new Query();
                query.addCriteria(Criteria.where("userId").is(user.getUserId())
                        .and("databaseId").is(user.getDatabaseId()));
                Update update = new Update();
                update.set("userId", user.getUserId());
                update.set("databaseId", user.getDatabaseId());
                update.set("colsName",user.getColsName());
                update.set("userName",user.getUserName());
                update.set("password",user.getPassword());
                mongoTemplate.updateFirst(query, update, User.class);
                updateUser.add(user);

            } catch (NotFoundException ex) {
                throw new NotFoundException("User with userId  " + user + " does not exist");

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
        return updateUser;
    }

    public void deleteUsers(User user) {
        try {
            Criteria criteria = Criteria.where("userId").is(user.getUserId())
                    .and("databaseId").is(user.getDatabaseId());
            Query query = Query.query(criteria);
            mongoTemplate.remove(query, User.class);
        } catch (DataAccessException ex) {
            throw new ConnectionException("Failed to delete interactions", ex);
        }
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
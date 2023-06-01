package com.noor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final MongoTemplate mongoTemplate;
    private final UserRepository userRepository;

    public UserService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    public User getUserById(String dataBaseId) {
        logger.info("Retrieving user with dataBaseId: {}", dataBaseId);
        return userRepository.findById(dataBaseId).orElse(null);
    }

    public List<User> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAll();
    }

    public User createUser(User user) {
        logger.info("Creating a new user with userID: {}", user.getUserID());
        return userRepository.save(user);
    }

    public User updateUser(String dataBaseId, User user) {
        User existingUser = userRepository.findById(dataBaseId).orElse(null);
        if(existingUser == null) {
            logger.warn("Could not find user with dataBaseId: {}", dataBaseId);
            return null;
        } else {
            logger.info("Updating user with dataBaseId: {}", dataBaseId);
            existingUser.setUserID(user.getUserID());
            existingUser.setUserName(user.getUserName());
            existingUser.setColsName(user.getColsName());
            existingUser.setPassword(user.getPassword());
            return userRepository.save(existingUser);
        }
    }

    public void deleteUser(String dataBaseId) {
        logger.info("Deleting user with dataBaseId: {}", dataBaseId);
        userRepository.deleteById(dataBaseId);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
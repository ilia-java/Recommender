package com.noor.user;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final MongoTemplate mongoTemplate;

    public UserService(MongoTemplate mongoTemplate, UserRepository userRepository) {
        this.mongoTemplate = mongoTemplate;
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;


        public User getUserById(String dataBaseId) {
            return userRepository.findById(dataBaseId).orElse(null);
        }

        public List<User> getAllUsers() {
            return userRepository.findAll();
        }

        public User createUser(User user) {
            return userRepository.save(user);
        }

        public User updateUser(String dataBaseId, User user) {
            User existingUser = userRepository.findById(dataBaseId).orElse(null);
            if (existingUser == null) {
                return null;
            } else {
                existingUser.setUserID(user.getUserID());
                existingUser.setUserName(user.getUserName());
                existingUser.setColsName(user.getColsName());
                existingUser.setPassword(user.getPassword());
                return userRepository.save(existingUser);
            }
        }

        public void deleteUser(String dataBaseId) {
            userRepository.deleteById(dataBaseId);
        }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
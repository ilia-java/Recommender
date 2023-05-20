package com.noor.user;

import com.noor.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {

        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return
                userRepository.findById(id).orElse(null);
    }
    public User getDataBaseId(String dataBaseId){
        return userRepository.findById(dataBaseId).orElse(null);
    }

    public void updateUser(User user) {

        userRepository.save(user);
    }

    public User addUser(User user) {
        return userRepository.insert(user);
    }
    public User addColName(User user) {
        return userRepository.insert(user);
    }

    public void deleteUserById(String id) {
        userRepository.deleteById(id);
    }
}

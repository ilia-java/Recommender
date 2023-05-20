package com.noor.user;

import com.noor.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping(value = "/user/controller")

public class UserController {
    @Autowired
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping(value = "/dataBaseId")
    public User getDataBaseId(@PathVariable String id){
        return userService.getDataBaseId(id);
    }


    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PostMapping("/users")
    public void saveUser(@RequestBody User user) {
        userService.addUser(user);
    }
    @PostMapping(value = ("/colName"))
    public User insertColName(@RequestBody User user){
        return userService.addColName(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
    }

    @PutMapping("/users/{id}")
    public void updateUser(@PathVariable String id, @RequestBody User user) {
        User existingUser = userService.getUserById(id);
        if (existingUser != null) {
            existingUser.setUserID(user.getUserID());
            existingUser.setUserName(user.getUserName());
            existingUser.setProperty(user.getProperty());
            existingUser.setPassword(user.getPassword());
            existingUser.setColsName(user.getColsName());
            existingUser.setDataBaseId(user.getDataBaseId());
            userService.updateUser(existingUser);
        }
    }
}

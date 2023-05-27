package com.noor.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;

@Component
@RestController
@RequestMapping(value = "/user/controller")

public class UserController {
    @Autowired
        private final UserService userService;

        public UserController(UserService userService) {
            this.userService = userService;
        }

        @GetMapping("/{dataBaseId}")
        public User getUserById(@PathVariable String dataBaseId) {
            return userService.getUserById(dataBaseId);
        }

        @GetMapping
        public List<User> getAllUsers() {
            return userService.getAllUsers();
        }

        @PostMapping
        @ResponseStatus(HttpStatus.CREATED)
        public User createUser(@RequestBody User user) {
            return userService.createUser(user);
        }

        @PutMapping("/{dataBaseId}")
        public User updateUser(@PathVariable String dataBaseId, @RequestBody User user) {
            return userService.updateUser(dataBaseId, user);
        }

        @DeleteMapping("/{dataBaseId}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public void deleteUser(@PathVariable String dataBaseId) {
            userService.deleteUser(dataBaseId);
        }
    }

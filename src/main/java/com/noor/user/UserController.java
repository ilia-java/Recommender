package com.noor.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@Component
@RestController
@RequestMapping(value = "/user/controller")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{dataBaseId}")
    public User getUserById(@PathVariable String dataBaseId) {
        logger.info(" GET  user with dataBaseId: {}", dataBaseId);
        return userService.getUserById(dataBaseId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.info(" GET  for all users");
        return userService.getAllUsers();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody User user) {
        logger.info("create a new user with userID:{}", user.getUserID());
        return userService.createUser(user);
    }

    @PutMapping("/{dataBaseId}")
    public ResponseEntity<User> updateUser(@PathVariable String dataBaseId, @RequestBody User user) {
        logger.info(" update user with dataBaseId: {}", dataBaseId);
        User userOutput = userService.updateUser(dataBaseId, user);
        if (userOutput == null) {
            logger.error("Could not find user with dataBaseId: {}", dataBaseId);
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(userOutput);
        }
    }

    @DeleteMapping("/{dataBaseId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable String dataBaseId) {
        logger.info( "delete user with dataBaseId: {}", dataBaseId);
        userService.deleteUser(dataBaseId);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleItemNotFoundException(Exception ex) {
        logger.error("Item not found: {}", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage());
    }

}
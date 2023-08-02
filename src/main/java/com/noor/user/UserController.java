package com.noor.user;

import com.noor.interactions.Interactions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value = "/get/{userId}/{databaseId}", produces = "application/json")
    public List<User> getUser(
            @PathVariable("userId") List<String> userIdList,
            @PathVariable("databaseId") List<String> databaseIdList) {
        List<User> users = userService.getUser(userIdList, databaseIdList);
        return users;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User users = userService.add(user);
        logger.info("Successfully created user with id: {}", users.get_id());
        return ResponseEntity.ok(users);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<User>> updateUser(@RequestBody List<User> users) {
        List<User> updatedUsers = userService.updateUsers(users);
        logger.info("Successfully updated User with userId: {}", users.stream().map(User::getUserId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(updatedUsers);
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        logger.info("Deleting user with id: {}", user.get_id());
        userService.deleteUsers(user);
        String message = "User with userId " + user.getUserId()  +  "and with databaseId"  +  user.getDatabaseId() +  " deleted successfully";
        return ResponseEntity.ok().body(message);
    }
}
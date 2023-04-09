package com.noor.mongo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/")
public class ItemController {

    private final ItemRepository userRepository;
    public ItemController(ItemRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ItemRepository getUserRepository() {
        return userRepository;
    }
}

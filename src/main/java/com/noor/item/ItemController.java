package com.noor.item;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;


@Component
@RequestMapping(value = "/item/controller")
@RestController
public class ItemController {
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{dataBaseId}")
    public Item getItemById(@PathVariable int dataBaseId) {
        logger.info("Getting item with id: {}", dataBaseId);
        return itemService.getItemById(dataBaseId);
    }

    @GetMapping
    public List<Item> getAllItems() {
        logger.info("Getting all items");
        return itemService.getAllItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody Item item) {
        logger.info("Creating new item: {}", item.getName());
        return itemService.createItem(item);
    }

    @PutMapping("/{dataBaseId}")
    public Item updateItem(@PathVariable int dataBaseId, @RequestBody Item item) {
        logger.info("Updating item with id: {}", dataBaseId);
        return itemService.updateItem(dataBaseId, item);
    }

    @DeleteMapping("/{dataBaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int dataBaseId) {
        logger.info("Deleting item with id: {}", dataBaseId);
        itemService.deleteItem(dataBaseId);
    }
}
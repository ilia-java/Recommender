package com.noor.item;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private final ItemService itemService;
    private final Logger logger = LoggerFactory.getLogger(ItemController.class);

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/get/{itemId}/{databaseId}", produces = "application/json")
    public List<Item> getUser(
            @PathVariable("itemId") List<String> itemIdList,
            @PathVariable("databaseId") List<String> databaseIdList) {
        List<Item> item = itemService.getItem(itemIdList, databaseIdList);
        return item;
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        Item createdItem = itemService.add(item);
        logger.info("Successfully created item with id: {}", createdItem.get_id());
        return ResponseEntity.ok(createdItem);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Item>> updateItem(@RequestBody List<Item> newItem) {
        List<Item> updatedItem = itemService.updateItem(newItem);
        logger.info("Successfully updated item with ids: {}", newItem.stream().map(Item::getItemId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(updatedItem);
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteInteraction(@RequestBody Item item) {
        logger.info("Deleting item with id: {}", item.getItemId());
        itemService.deleteItem(item);
        String message = "Item with itemId " +  item.getItemId()   + " deleted successfully";
        return ResponseEntity.ok().body(message);
    }

}
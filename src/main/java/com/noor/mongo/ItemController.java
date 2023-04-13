package com.noor.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping(value = "add")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.ok(itemService.addItem(item));
    }

    @DeleteMapping(value = "delete")
    public ResponseEntity<Boolean> deleteItem(@PathVariable("/{id}") String id) {
        return ResponseEntity.ok(itemService.deleteItem(id));
    }

    @GetMapping(value = "get")
    public ResponseEntity<Item> getIem(@PathVariable("/{id}") String id) {
        return ResponseEntity.ok(itemService.getItem(id));
    }
}

package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;


@Component
@RequestMapping(value = "/item/controller")
@RestController
public class ItemController {
@Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/{dataBaseId}")
    public Item getItemById(@PathVariable int dataBaseId) {
        return itemService.getItemById(dataBaseId);
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Item createItem(@RequestBody Item item) {
        return itemService.createItem(item);
    }

    @PutMapping("/{dataBaseId}")
    public Item updateItem(@PathVariable int dataBaseId, @RequestBody Item item) {
        return itemService.updateItem(dataBaseId, item);
    }

    @DeleteMapping("/{dataBaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable int dataBaseId) {
        itemService.deleteItem(dataBaseId);
    }
}
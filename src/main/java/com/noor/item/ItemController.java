package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Component
@RestController
@RequestMapping(value = "/item/controller")
public class ItemController {
    @Autowired
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping(value = "/items")
    public @ResponseBody Iterable<Item> getAllItem() {
        return itemService.getAllUsers();
    }

    @GetMapping(value = "view/{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemService.getById(id);
    }

    @PostMapping(value = "/add")
    public Item createNewItem(@RequestBody Item newItem) {
        return itemService.addItem(newItem);
    }


    @PutMapping("/update/{id}")
    public void updateUser(@PathVariable int id, @RequestBody Item item) {
        Item existingUser = itemService.getById(id);
        if (existingUser != null) {
            existingUser.setItemId(item.getItemId());
            existingUser.setName(item.getName());
            existingUser.setColsName(item.getColsName());
            itemService.updateItem(existingUser);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable int id) {
        itemService.deleteItem(id);
    }

}
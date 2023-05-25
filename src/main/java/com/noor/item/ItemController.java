package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

// write get value by item id baraye user ham bezar
//config mongo va ezafe kardane kelase jadid
//logger
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
        return itemService.getAllItem();
    }

    @GetMapping(value = "view/{id}")
    public Item getItem(@PathVariable Integer id) {
        return itemService.getById(id);
    }

    @GetMapping(value = "/dataBaseId")
    public Item getDataBaseId(@PathVariable int id) {
        return itemService.getDataBaseId(id);
    }

    @PostMapping(value = "/add")
    public Item createNewItem(@RequestBody Item newItem) {
        return itemService.addItem(newItem);
    }

    @PostMapping(value = ("/colName"))
    public Item insertColName(@RequestBody Item item) {
        return itemService.addColName(item);
    }

    @PutMapping("/update/{id}")
    public void updateItem(@PathVariable int id, @RequestBody Item item) {
        Item existingUser = itemService.getById(id);
        if (existingUser != null) {
            existingUser.setItemId(item.getItemId());
            existingUser.setName(item.getName());
            existingUser.setColsName(item.getColsName());
            existingUser.setDataBaseId(item.getDataBaseId());
            itemService.updateItem(existingUser);
        }
    }

    @DeleteMapping("/delete/{id}")
    public void deleteUserById(@PathVariable int id) {
        itemService.deleteItem(id);
    }

}
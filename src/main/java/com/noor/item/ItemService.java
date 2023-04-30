package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import java.util.List;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllUsers() {
        return itemRepository.findAll();
    }

    public Item getById(int id) {
        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(Item item) {
        itemRepository.save(item);

    }

    public Item addItem(Item item) {
        return itemRepository.insert(item);
    }

    public void deleteItem(int id) {
        itemRepository.deleteById(id);
    }

}

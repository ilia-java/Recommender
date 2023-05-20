package com.noor.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItem() {
        return itemRepository.findAll();
    }

    public Item getById(int id) {

        return itemRepository.findById(id).orElse(null);
    }

    public void updateItem(Item item) {
        itemRepository.save(item);

    }
    public Item getDataBaseId(int dataBaseId){
        return itemRepository.findById(dataBaseId).orElse(null);
    }

    public Item addItem(Item item) {

        return itemRepository.insert(item);
    }

    public void deleteItem(int id) {

        itemRepository.deleteById(id);
    }

    public Item addColName(Item item) {

        return itemRepository.insert(item);
    }
}

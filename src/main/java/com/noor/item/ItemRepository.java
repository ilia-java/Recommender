package com.noor.item;


import com.noor.interactions.Interactions;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends MongoRepository<Item, Integer> {


}
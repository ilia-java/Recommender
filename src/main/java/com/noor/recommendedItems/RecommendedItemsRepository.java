package com.noor.recommendedItems;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RecommendedItemsRepository extends MongoRepository<RecommendedItems,String> {
    Optional<RecommendedItems> findById(String dataBaseId);

}

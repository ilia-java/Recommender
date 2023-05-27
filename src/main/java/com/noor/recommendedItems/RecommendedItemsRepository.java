package com.noor.recommendedItems;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecommendedItemsRepository extends MongoRepository<RecommendedItems,String> {
}

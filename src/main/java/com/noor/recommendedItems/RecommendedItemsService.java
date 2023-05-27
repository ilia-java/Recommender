package com.noor.recommendedItems;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendedItemsService {
    private final MongoTemplate mongoTemplate;
    private RecommendedItemsRepository recommendedItemsRepository;


    public RecommendedItemsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public List<RecommendedItems> getAllRecommendedItems() {
        return recommendedItemsRepository.findAll();
    }

    public RecommendedItems getRecommendedItemsById(String id) {
        return recommendedItemsRepository.findById(id).orElse(null);
    }

    public RecommendedItems addRecommendedItems(RecommendedItems recommendedItems) {
        return recommendedItemsRepository.save(recommendedItems);
    }

    public RecommendedItems updateRecommendedItems(String id, RecommendedItems newRecommendedItems) {
        Optional<RecommendedItems> optionalRecommendedItems = recommendedItemsRepository.findById(id);
        if (optionalRecommendedItems.isPresent()) {
            RecommendedItems recommendedItems = optionalRecommendedItems.get();
            recommendedItems.setCount(newRecommendedItems.getCount());
            recommendedItems.setCascadeCreate(newRecommendedItems.getCascadeCreate());
            recommendedItems.setDataBaseId(newRecommendedItems.getDataBaseId());
            recommendedItems.setUserId(newRecommendedItems.getUserId());
            recommendedItems.setScenario(newRecommendedItems.getScenario());
            return recommendedItemsRepository.save(recommendedItems);
        } else {
            return null;
        }

    }
    public void deleteRecommendedItems(String id){
        recommendedItemsRepository.deleteById(id);
    }
}

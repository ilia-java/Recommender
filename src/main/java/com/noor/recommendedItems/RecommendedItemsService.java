package com.noor.recommendedItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecommendedItemsService {
    private static final Logger logger = LoggerFactory.getLogger(RecommendedItemsService.class);

    private RecommendedItemsRepository recommendedItemsRepository;

    public RecommendedItemsService(RecommendedItemsRepository recommendedItemsRepository) {
        this.recommendedItemsRepository = recommendedItemsRepository;
    }

    public List<RecommendedItems> getAllRecommendedItems() {
        logger.info("Getting all recommended items");
        return recommendedItemsRepository.findAll();
    }

    public RecommendedItems getRecommendedItemsById(String id) {
        logger.info("Getting recommended item with id: {}", id);
        Optional<RecommendedItems> optionalRecommendedItems = recommendedItemsRepository.findById(id);
        return optionalRecommendedItems.orElse(null);
    }

    public RecommendedItems addRecommendedItems(RecommendedItems recommendedItems) {
        logger.info("Adding new recommended item with id: {}", recommendedItems.getDataBaseId());
        return recommendedItemsRepository.save(recommendedItems);
    }

    public RecommendedItems updateRecommendedItems(String id, RecommendedItems newRecommendedItems) {
        logger.info("Updating recommended item with id: {}", id);
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
            logger.warn("Recommended item with id {} does not exist", id);
            return null;
        }
    }

    public void deleteRecommendedItems(String id) {
        logger.info("Deleting recommended item with id: {}", id);
        recommendedItemsRepository.deleteById(id);
    }
}
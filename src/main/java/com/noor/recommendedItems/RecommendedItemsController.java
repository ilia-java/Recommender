package com.noor.recommendedItems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommended-items")
public class RecommendedItemsController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendedItemsController.class);

    @Autowired
    private final RecommendedItemsService recommendedItemsService;

    public RecommendedItemsController(RecommendedItemsService recommendedItemsService) {
        this.recommendedItemsService = recommendedItemsService;
    }

    @GetMapping("/{dataBaseId}")
    public RecommendedItems getRecommendedItemById(@PathVariable String dataBaseId) {
        logger.info("Getting recommended item with id: {}", dataBaseId);
        return recommendedItemsService.getRecommendedItemsById(dataBaseId);
    }

    @GetMapping
    public List<RecommendedItems> getAllRecommendedItems() {
        logger.info("Getting all recommended items");
        return recommendedItemsService.getAllRecommendedItems();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecommendedItems createRecommendedItem(@RequestBody RecommendedItems recommendedItem) {
        logger.info("Adding new recommended item  with dataBaseId: {}", recommendedItem.getDataBaseId());
        return recommendedItemsService.addRecommendedItems(recommendedItem);
    }

    @PutMapping("/{dataBaseId}")
    public RecommendedItems updateRecommendedItem(@PathVariable String dataBaseId, @RequestBody RecommendedItems recommendedItem) {
        logger.info(" update recommended item with dataBaseId: {}", dataBaseId);
        return recommendedItemsService.updateRecommendedItems(dataBaseId, recommendedItem);
    }

    @DeleteMapping("/{dataBaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecommendedItem(@PathVariable String dataBaseId){
        logger.info("delete recommended item with dataBaseId: {}", dataBaseId);
        recommendedItemsService.deleteRecommendedItems(dataBaseId);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleItemNotFoundException(Exception ex) {
        logger.error("Item not found: {}", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception ex) {
        logger.error("Unexpected error occurred: {}", ex.getMessage());
    }

}
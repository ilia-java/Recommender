package com.noor.recommendedItems;

import com.noor.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recommendedItems")
public class RecommendedItemsController {
    private static final Logger logger = LoggerFactory.getLogger(RecommendedItemsController.class);

    @Autowired
    private final RecommendedItemsService recommendedItemsService;

    public RecommendedItemsController(RecommendedItemsService recommendedItemsService) {
        this.recommendedItemsService = recommendedItemsService;
    }

    @GetMapping(value = "/get/{userId}/{databaseId}/{count}", produces = "application/json")
    public List<RecommendedItems> get(
            @PathVariable("userId") List<String> userIdList,
            @PathVariable("databaseId") List<String> databaseIdList,
            @PathVariable("count") List<Integer> countList) {
        return recommendedItemsService.getRecommendedItem(userIdList, databaseIdList, countList);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecommendedItems> createRecommendedItem(@RequestBody RecommendedItems recommendedItem) {
        logger.info("Creating new recommended item with dataBaseId: {}", recommendedItem.getDatabaseId());
        RecommendedItems createdRecommendedItem = recommendedItemsService.addRecommendedItems(recommendedItem);
        return ResponseEntity.ok(createdRecommendedItem);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<RecommendedItems>> updateRecommendedItem(@RequestBody List<RecommendedItems> recommendedItems) {
        List<RecommendedItems> updateRecommendedItems = recommendedItemsService.updateRecommendedItems(recommendedItems);
        logger.info("Successfully updated User with ids: {}", recommendedItems.stream().map(RecommendedItems::getUserId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(updateRecommendedItems);
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteRecommendedItems(@RequestBody RecommendedItems recommendedItems) {
        logger.info("Deleting user with id: {}", recommendedItems.getUserId());
        recommendedItemsService.deleteRecommendedItems(recommendedItems);
        String message = "RecommendedItem with userId " + recommendedItems.getUserId() + "and with databaseId" + recommendedItems.getDatabaseId() + " deleted successfully";
        return ResponseEntity.ok().body(message);
    }
}
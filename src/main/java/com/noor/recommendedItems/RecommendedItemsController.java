package com.noor.recommendedItems;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommended-items")
public class RecommendedItemsController {

    @Autowired
    private final RecommendedItemsService recommendedItemsService;

    public RecommendedItemsController(RecommendedItemsService recommendedItemsService) {
        this.recommendedItemsService = recommendedItemsService;
    }

    @GetMapping("/{dataBaseId}")
    public RecommendedItems getRecommendedItemById(@PathVariable String dataBaseId) {
        return recommendedItemsService.getRecommendedItemsById(dataBaseId);
    }

    @GetMapping
    public List<RecommendedItems> getAllRecommendedItems() {
        return recommendedItemsService.getAllRecommendedItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecommendedItems createRecommendedItem(@RequestBody RecommendedItems recommendedItem) {
        return recommendedItemsService.addRecommendedItems(recommendedItem);
    }

    @PutMapping("/{dataBaseId}")
    public RecommendedItems updateRecommendedItem(@PathVariable String dataBaseId, @RequestBody RecommendedItems recommendedItem) {
        return recommendedItemsService.updateRecommendedItems(dataBaseId, recommendedItem);
    }

    @DeleteMapping("/{dataBaseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRecommendedItem(@PathVariable String dataBaseId){
        recommendedItemsService.deleteRecommendedItems(dataBaseId);}
}
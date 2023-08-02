package com.noor.interactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/interactions")
public class InteractionsController {

    private static final Logger logger = LoggerFactory.getLogger(InteractionsController.class);

    private final InteractionsService interactionsService;

    public InteractionsController(InteractionsService interactionsService) {
        this.interactionsService = interactionsService;
    }

    @GetMapping(value = "/get/{itemId}/{userId}/{databaseId}", produces = "application/json")
    public List<Interactions> getInteractions(
            @PathVariable("itemId") List<String> itemIdList,
            @PathVariable("userId") List<String> userIdList,
            @PathVariable("databaseId") List<String> databaseIdList){
        return interactionsService.getInteractions(itemIdList, userIdList, databaseIdList);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Interactions> createInteraction(@RequestBody Interactions interaction) {
        Interactions createdInteraction = interactionsService.add(interaction);
        logger.info("Successfully created interaction with id: {}", createdInteraction.get_id());
        return ResponseEntity.ok(createdInteraction);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<Interactions>> updateInteractions(@RequestBody List<Interactions> newInteractions) {
        List<Interactions> updatedInteractions = interactionsService.updateInteractions(newInteractions);
        logger.info("Successfully updated interactions with ids: {}", newInteractions.stream().map(Interactions::getItemId).collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK).body(updatedInteractions);
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> deleteInteraction(@RequestBody Interactions interactions) {
        logger.info("Deleting interaction with id: {}", interactions.getItemId());
        interactionsService.deleteInteractions(interactions);
        String message = "Interaction with id " + interactions.getItemId() + "and with itemId" + interactions.getItemId() + "and with databaseId" + interactions.getDatabaseId() + " deleted successfully";
        return ResponseEntity.ok().body(message);
    }
}
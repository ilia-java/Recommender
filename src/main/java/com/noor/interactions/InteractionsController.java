package com.noor.interactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interactions")
public class InteractionsController {

    private static final Logger logger = LoggerFactory.getLogger(InteractionsController.class);

    @Autowired
    private InteractionsService interactionsService;

    @GetMapping
    public List<Interactions> getAllInteractions() {
        logger.info("Getting all interactions");
        return interactionsService.getAllInteractions();
    }

    @GetMapping("/{id}")
    public Interactions getInteractionById(@PathVariable("id") String id) {
        logger.info("Getting interaction with id: {}", id);
        return interactionsService.getInteractionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Interactions addInteraction(@RequestBody Interactions interaction) {
        logger.info("Adding new interaction with id: {}", interaction.getDatabaseID());
        return interactionsService.addInteraction(interaction);
    }

    @PutMapping("/{id}")
    public Interactions updateInteraction(@PathVariable("id") String id, @RequestBody Interactions newInteraction) {
        logger.info("Updating interaction with id:{}", id);
        return interactionsService.updateInteraction(id, newInteraction);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInteraction(@PathVariable("id") String id) {
        logger.info("Deleting interaction with id: {}", id);
        interactionsService.deleteInteraction(id);
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
package com.noor.interactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interactions")
public class InteractionsController {

    @Autowired
    private InteractionsService interactionsService;

    @GetMapping
    public List<Interactions> getAllInteractions() {
        return interactionsService.getAllInteractions();
    }

    @GetMapping("/{id}")
    public Interactions getInteractionById(@PathVariable("id") String id) {
        return interactionsService.getInteractionById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Interactions addInteraction(@RequestBody Interactions interaction) {
        return interactionsService.addInteraction(interaction);
    }

    @PutMapping("/{id}")
    public Interactions updateInteraction(@PathVariable("id") String id, @RequestBody Interactions newInteraction) {
        return interactionsService.updateInteraction(id, newInteraction);

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInteraction(@PathVariable("id") String id) {
        interactionsService.deleteInteraction(id);
    }
}
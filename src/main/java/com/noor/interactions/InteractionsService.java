package com.noor.interactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionsService {
    private final MongoTemplate mongoTemplate;
    @Autowired
    private InteractionsRepository interactionsRepository;

    public InteractionsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Interactions> getAllInteractions() {
        return interactionsRepository.findAll();
    }

    public Interactions getInteractionById(String id) {
        return interactionsRepository.findById(id).orElse(null);
    }

    public Interactions addInteraction(Interactions interaction) {
        return interactionsRepository.save(interaction);
    }

    public Interactions updateInteraction(String id, Interactions newInteraction) {
        Optional<Interactions> optionalInteraction = interactionsRepository.findById(id);
        if (optionalInteraction.isPresent()) {
            Interactions interaction = optionalInteraction.get();
            interaction.setItemID(newInteraction.getItemID());
            interaction.setUserID(newInteraction.getUserID());
            interaction.setRating(newInteraction.getRating());
            interaction.setTimeStamp(newInteraction.getTimeStamp());
            return interactionsRepository.save(interaction);
        } else {
            return null;
        }
    }

    public void deleteInteraction(String id) {
        interactionsRepository.deleteById(id);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
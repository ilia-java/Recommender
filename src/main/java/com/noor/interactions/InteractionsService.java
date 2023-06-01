package com.noor.interactions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InteractionsService {
    private final MongoTemplate mongoTemplate;
    private final Logger logger = LoggerFactory.getLogger(InteractionsService.class);
    @Autowired
    private InteractionsRepository interactionsRepository;

    public InteractionsService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Interactions> getAllInteractions() {
        logger.info("Getting all interactions");
        return interactionsRepository.findAll();
    }

    public Interactions getInteractionById(String id) {
        logger.info("Getting interaction by id: {}", id);
        return interactionsRepository.findById(id).orElse(null);
    }

    public Interactions addInteraction(Interactions interaction) {
        logger.info("Adding new interaction for user {} and item {}", interaction.getUserID(), interaction.getItemID());
        return interactionsRepository.save(interaction);
    }

    public Interactions updateInteraction(String id, Interactions newInteraction) {
        logger.info("Updating interaction with id: {}", id);
        Optional<Interactions> optionalInteraction = interactionsRepository.findById(id);
        if (optionalInteraction.isPresent()) {
            Interactions interaction = optionalInteraction.get();
            interaction.setItemID(newInteraction.getItemID());
            interaction.setUserID(newInteraction.getUserID());
            interaction.setRating(newInteraction.getRating());
            interaction.setTimeStamp(newInteraction.getTimeStamp());
            return interactionsRepository.save(interaction);
        } else {
            logger.warn("Interaction with id {} does not exist", id);
            return null;
        }
    }

    public void deleteInteraction(String id) {
        logger.info("Deleting interaction with id: {}", id);
        interactionsRepository.deleteById(id);
    }

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }
}
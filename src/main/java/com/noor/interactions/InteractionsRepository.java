package com.noor.interactions;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InteractionsRepository extends MongoRepository<Interactions, String> {
    List<Interactions> findAll();

    Optional<Interactions> findById(String s);
}
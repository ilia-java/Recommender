package com.noor.interactions;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface InteractionsRepository extends MongoRepository<Interactions,String> {
}

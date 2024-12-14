package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {
}

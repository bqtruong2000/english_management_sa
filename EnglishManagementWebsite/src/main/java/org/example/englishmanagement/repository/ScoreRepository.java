package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Score;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreRepository extends MongoRepository<Score, String> {
}


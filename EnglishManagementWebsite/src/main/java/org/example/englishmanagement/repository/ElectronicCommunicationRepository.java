package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.ElectronicCommunication;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ElectronicCommunicationRepository extends MongoRepository<ElectronicCommunication, String> {
}

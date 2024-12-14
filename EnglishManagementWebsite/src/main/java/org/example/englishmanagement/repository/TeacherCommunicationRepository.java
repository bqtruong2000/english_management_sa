package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.TeacherCommunication;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherCommunicationRepository extends MongoRepository<TeacherCommunication, String> {
}


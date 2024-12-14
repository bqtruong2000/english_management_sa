package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.ScoreManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoreManagementRepository extends MongoRepository<ScoreManagement, String> {
    Optional<ScoreManagement> findByClassIdAndStudentId(String classId, String studentId);
}


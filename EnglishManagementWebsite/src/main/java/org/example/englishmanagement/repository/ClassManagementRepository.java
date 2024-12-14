package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.ClassManagement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClassManagementRepository extends MongoRepository<ClassManagement, String> {
}

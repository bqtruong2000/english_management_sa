package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.StudentManagement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentManagementRepository extends MongoRepository<StudentManagement, String> {
    Optional<StudentManagement> findByIdStudent(String idStudent);

}

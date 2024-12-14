package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.TeacherManagement;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TeacherManagementRepository extends MongoRepository<TeacherManagement, String> {
    Optional<TeacherManagement> findByIdTeacher(String idTeacher);
}

package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Class;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassRepository extends MongoRepository<Class, String> {
    List<Class> findByListStudentContaining(String studentId);
    List<Class> findByIdTeacher(String teacherId);
}

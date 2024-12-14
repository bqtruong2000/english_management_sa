package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.ClassRegistration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClassRegistrationRepository extends MongoRepository<ClassRegistration, String> {
    List<ClassRegistration> findByClassIdAndApproved(String classId, boolean approved);

    List<ClassRegistration> findByStudentId(String studentId);
}

package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NotificationRepository extends MongoRepository<Notification, String> {
    List<Notification> findByClassId(String classId); // Custom query method
}

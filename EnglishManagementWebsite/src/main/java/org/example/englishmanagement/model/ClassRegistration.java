package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "class_registration")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassRegistration {
    @Id
    private String id;
    private String classId; // Primary Key for class registration
    private String studentId; // Student who registered
    private boolean approved; // Approval status
}

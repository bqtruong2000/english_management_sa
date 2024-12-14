package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teacher_communications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherCommunication {
    @Id
    private String id; // Unique identifier
    private String scoreId; // Associated score ID
    private String classId; // Associated class ID
    private String teacherId; // Teacher ID
}


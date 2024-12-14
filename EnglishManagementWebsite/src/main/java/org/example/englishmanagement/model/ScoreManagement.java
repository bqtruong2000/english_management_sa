package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "score_managements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScoreManagement {
    @Id
    private String id; // Unique identifier for this management record
    private String scoreId; // ID referencing the associated Score
    private String roleId; // Role of the user (student/teacher/admin)
    private String classId; // ID of the class
    private String studentId; // ID of the student
}



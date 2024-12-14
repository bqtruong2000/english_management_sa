package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "teachers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeacherManagement {
    @Id
    private String idTeacher; // References the ID from Auth/Profile
    private String classId;   // References the class they are teaching
}

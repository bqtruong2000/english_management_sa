package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentManagement {
    @Id
    private String idStudent;
    private List<String> classIds;  // Changed from String to List<String>
    private Profile profile;

}
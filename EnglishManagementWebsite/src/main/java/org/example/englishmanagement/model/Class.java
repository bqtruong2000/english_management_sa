package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "classes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Class {
    @Id
    private String id; // Class ID (Primary Key)
    private String className;
    private String room;
    private String duration;
    private String idTeacher; // Teacher ID
    private List<String> listStudent; // List of student IDs
}

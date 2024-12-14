package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "classManagement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassManagement {
    @Id
    private String courseId; // Associated course ID
    private List<String> classIds; // List of associated class IDs
    private boolean status;
}

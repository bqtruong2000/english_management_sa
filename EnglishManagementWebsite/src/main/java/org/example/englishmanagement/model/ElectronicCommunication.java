package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "electronic_communication")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectronicCommunication {
    @Id
    private String id; // Primary key
    private String studentId; // Associated Student ID
}

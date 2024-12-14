package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Score {
    @Id
    private String id; // Unique identifier for the score
    private float midterm; // Midterm score
    private float finalExam; // Final exam score

    public float average() {
        return (midterm + finalExam) / 2;
    }
}



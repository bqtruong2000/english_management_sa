package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token {
    @Id
    private String id;
    private String roleId;
    private String userId;
    private String jwt;
}

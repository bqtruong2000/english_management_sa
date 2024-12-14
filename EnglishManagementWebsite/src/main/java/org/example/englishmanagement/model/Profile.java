package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {
    @Id
    private String id;
    private String code;
    private String firstName;
    private String lastName;
    private String dob;
    private String email;
    private String phoneNum;
    private String avatar;
}


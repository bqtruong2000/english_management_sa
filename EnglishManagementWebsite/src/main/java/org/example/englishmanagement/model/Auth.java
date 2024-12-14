package org.example.englishmanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "auth")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Auth {
    @Id
    private String id;
    private String roleId;
    private String userName;
    private String password;
    private String hPassword;
    private boolean isDeleted;
}

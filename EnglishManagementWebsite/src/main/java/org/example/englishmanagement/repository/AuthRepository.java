package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Auth;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthRepository extends MongoRepository<Auth, String> {
    Auth findByUserName(String userName);
    Iterable<Auth> findAllByRoleIdAndIsDeletedFalse(String roleId);
    long countByRoleId(String roleId); // Use roleId instead of role

}
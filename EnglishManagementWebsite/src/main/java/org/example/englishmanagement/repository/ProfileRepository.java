package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Profile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<Profile, String> {
    Optional<Profile> findByCode(String code);
    Optional<Profile> findById(String id);

}

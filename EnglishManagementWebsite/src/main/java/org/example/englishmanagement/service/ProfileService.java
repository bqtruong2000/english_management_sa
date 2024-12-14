package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Profile;
import org.example.englishmanagement.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;


    public Profile createProfile(Profile profile) {
        if (profileRepository.findByCode(profile.getCode()).isPresent()) {
            throw new RuntimeException("Profile with this code already exists!");
        }
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Profile profile) {
        Optional<Profile> existingProfile = profileRepository.findByCode(profile.getCode());
        if (existingProfile.isEmpty()) {
            throw new RuntimeException("Profile not found!");
        }

        Profile updatedProfile = existingProfile.get();
        updatedProfile.setFirstName(profile.getFirstName());
        updatedProfile.setLastName(profile.getLastName());
        updatedProfile.setDob(profile.getDob());
        updatedProfile.setEmail(profile.getEmail());
        updatedProfile.setPhoneNum(profile.getPhoneNum());
        updatedProfile.setAvatar(profile.getAvatar());

        return profileRepository.save(updatedProfile);
    }

    public Profile getProfile(String code) {
        return profileRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Profile not found!"));
    }

    public boolean profileExists(String code) {
        return profileRepository.findByCode(code).isPresent();
    }

    public Profile getProfileById(String id) {
        return profileRepository.findByCode (id)
                .orElseThrow(() -> new RuntimeException("Profile not found for ID: " + id));
    }

    public Profile createProfileForStudent(Profile profile) {
        // Check if a profile with the given ID already exists
        if (profileRepository.findById(profile.getId()).isPresent()) {
            throw new RuntimeException("Profile with this ID already exists!");
        }

        // Save the profile with the same ID as Auth
        return profileRepository.save(profile);
    }



}

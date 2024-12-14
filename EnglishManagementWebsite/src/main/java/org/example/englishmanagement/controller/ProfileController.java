package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Profile;
import org.example.englishmanagement.service.ProfileService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/get/{code}")
    public ResponseEntity<?> getProfileByCode(@PathVariable String code) {
        return ResponseEntity.ok(profileService.getProfile(code));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token) {
        // Extract user code from the token (this assumes the code is stored in the token)
        String userCode = JwtUtil.extractCode(token); // Implement TokenUtil for this
        profile.setCode(userCode);

        return ResponseEntity.ok(profileService.createProfile(profile));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token) {
        String userCode = JwtUtil.extractCode(token);
        profile.setCode(userCode);

        return ResponseEntity.ok(profileService.updateProfile(profile));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String userCode = JwtUtil.extractCode(token);
        return ResponseEntity.ok(profileService.getProfile(userCode));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<Profile> getProfileByStudentId(@PathVariable String studentId) {
        Profile profile = profileService.getProfile(studentId); // `studentId` maps to `code`
        return ResponseEntity.ok(profile);
    }


    @PostMapping("/student/create/{id}")
    public ResponseEntity<?> createProfileForStudent(@PathVariable String id, @RequestBody Profile profile) {
        try {
            // Set the ID of the profile to match the student's Auth ID
            profile.setId(id);
            Profile createdProfile = profileService.createProfileForStudent(profile);
            return ResponseEntity.ok(createdProfile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}

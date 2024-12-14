package org.example.englishmanagement.controller;

import org.example.englishmanagement.model.Notification;
import org.example.englishmanagement.model.Profile;
import org.example.englishmanagement.service.ElectronicCommunicationService;
import org.example.englishmanagement.service.ProfileService;
import org.example.englishmanagement.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/electronic-communication")
public class ElectronicCommunicationController {
    @Autowired
    private ElectronicCommunicationService electronicCommunicationService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/view-notification/{classId}")
    public ResponseEntity<?> viewNotification(@PathVariable String classId) {
        List<Notification> notifications = electronicCommunicationService.viewNotification(classId);
        return ResponseEntity.ok(notifications); // Directly returns the list of notifications
    }


    @GetMapping("/get-score/{classId}")
    public ResponseEntity<?> getScore(@RequestHeader("Authorization") String token, @PathVariable String classId) {
        String studentId = JwtUtil.extractCode(token); // Student ID from JWT
        return ResponseEntity.ok(electronicCommunicationService.getScore(studentId, classId));
    }

    @PostMapping("/create-profile")
    public ResponseEntity<?> createProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token) {
        // Extract user code from the token (this assumes the code is stored in the token)
        String userCode = JwtUtil.extractCode(token); // Implement TokenUtil for this
        profile.setCode(userCode);

        return ResponseEntity.ok(electronicCommunicationService.createProfile(profile));
    }

    @GetMapping("/get-profile")
    public ResponseEntity<?> getProfile(@RequestHeader("Authorization") String token) {
        String userCode = JwtUtil.extractCode(token);
        return ResponseEntity.ok(electronicCommunicationService.getProfile(userCode));
    }

    @PutMapping("/edit-profile")
    public ResponseEntity<?> editProfile(@RequestBody Profile profile, @RequestHeader("Authorization") String token) {
        String userCode = JwtUtil.extractCode(token);
        profile.setCode(userCode);
        return ResponseEntity.ok(electronicCommunicationService.editProfile(profile));
    }
}

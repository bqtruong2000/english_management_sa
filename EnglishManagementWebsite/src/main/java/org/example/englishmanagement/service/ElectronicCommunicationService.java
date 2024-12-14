package org.example.englishmanagement.service;

import org.example.englishmanagement.model.ElectronicCommunication;
import org.example.englishmanagement.model.Notification;
import org.example.englishmanagement.model.Profile;
import org.example.englishmanagement.model.Score;
import org.example.englishmanagement.repository.ElectronicCommunicationRepository;
import org.example.englishmanagement.repository.NotificationRepository;
import org.example.englishmanagement.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ElectronicCommunicationService {
    @Autowired
    private ElectronicCommunicationRepository electronicCommunicationRepository;

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ScoreManagementService scoreManagementService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private ProfileService profileService;

    public Profile getProfile(String code) {
        return profileService.getProfile(code);
    }

    public Profile editProfile(Profile profile) {
        // Delegate to ProfileService
        return profileService.updateProfile(profile);
    }

    public Profile createProfile(Profile profile) {
        return profileService.createProfile(profile);
    }

    public List<Notification> viewNotification(String classId) {
        List<Notification> notifications = notificationRepository.findByClassId(classId);

        if (notifications.isEmpty()) {
            throw new RuntimeException("No notifications found for the given class ID!");
        }

        return notifications;
    }


    public Optional<Score> getScore(String studentId, String classId) {
        return scoreManagementService.getScore(classId, studentId);
    }
}

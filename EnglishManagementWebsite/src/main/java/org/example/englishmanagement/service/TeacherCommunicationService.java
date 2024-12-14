package org.example.englishmanagement.service;

import org.example.englishmanagement.model.Notification;
import org.example.englishmanagement.model.Score;
import org.example.englishmanagement.model.ScoreManagement;
import org.example.englishmanagement.model.Class;
import org.example.englishmanagement.repository.ClassRepository;
import org.example.englishmanagement.repository.NotificationRepository;
import org.example.englishmanagement.repository.ScoreManagementRepository;
import org.example.englishmanagement.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeacherCommunicationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private ScoreManagementService scoreManagementService;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private ScoreManagementRepository scoreManagementRepository;

    public void sendNotification(String classId, String content) {
        Notification notification = Notification.builder()
                .classId(classId)
                .content(content)
                .timestamp(LocalDateTime.now()) // Add the current timestamp
                .build();
        notificationRepository.save(notification);
    }

    public Optional<Score> getScore(String classId, String studentId) {
        return scoreManagementService.getScore(classId, studentId);
    }

    public void editScore(String classId, String studentId, float midterm, float finalExam) {
        Score score = scoreManagementService.getScore(classId, studentId)
                .orElseThrow(() -> new RuntimeException("Score not found!"));

        score.setMidterm(midterm);
        score.setFinalExam(finalExam);
        scoreRepository.save(score);
    }

    public void createScore(String classId, String studentId) {
        // Validate class existence
        Class classObj = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found!"));

        // Validate student existence in class
        if (!classObj.getListStudent().contains(studentId)) {
            throw new RuntimeException("Student is not enrolled in the class!");
        }

        // Check if a score for this student in this class already exists
        Optional<ScoreManagement> existingScore = scoreManagementRepository.findByClassIdAndStudentId(classId, studentId);
        if (existingScore.isPresent()) {
            throw new RuntimeException("Score already exists for this student in the class!");
        }

        // Create and save the new Score
        Score newScore = Score.builder()
                .id(UUID.randomUUID().toString())
                .midterm(0.0f) // Default initial value
                .finalExam(0.0f) // Default initial value
                .build();
        scoreRepository.save(newScore);

        // Create and save the ScoreManagement entry
        ScoreManagement scoreManagement = ScoreManagement.builder()
                .id(UUID.randomUUID().toString())
                .scoreId(newScore.getId())
                .roleId("student")
                .classId(classId)
                .studentId(studentId)
                .build();
        scoreManagementRepository.save(scoreManagement);
    }


}



package org.example.englishmanagement.service;
import org.example.englishmanagement.model.Score;
import org.example.englishmanagement.model.ScoreManagement;
import org.example.englishmanagement.repository.ScoreManagementRepository;
import org.example.englishmanagement.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ScoreManagementService {
    @Autowired
    private ScoreManagementRepository scoreManagementRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    public Optional<Score> getScore(String classId, String studentId) {
        ScoreManagement scoreManagement = scoreManagementRepository
                .findByClassIdAndStudentId(classId, studentId)
                .orElseThrow(() -> new RuntimeException("ScoreManagement record not found!"));

        return scoreRepository.findById(scoreManagement.getScoreId());
    }
}


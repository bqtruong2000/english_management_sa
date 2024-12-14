package org.example.englishmanagement.service;

import org.example.englishmanagement.model.ClassRegistration;
import org.example.englishmanagement.model.StudentManagement;
import org.example.englishmanagement.repository.ClassRegistrationRepository;
import org.example.englishmanagement.repository.StudentManagementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClassRegistrationService {
    @Autowired
    private ClassRegistrationRepository classRegistrationRepository;

    @Autowired
    private StudentManagementRepository studentManagementRepository;

    /**
     * Get the list of students in the waiting list for a specific class.
     * @param classId The class ID.
     * @return List of students in the waiting list.
     */
    @Transactional(readOnly = true)
    public List<StudentManagement> getStudentsInWaitingList(String classId) {
        // Get all unapproved registrations for the given class
        List<ClassRegistration> waitingRegistrations = classRegistrationRepository.findByClassIdAndApproved(classId, false);

        // Fetch student details for each registration
        return waitingRegistrations.stream()
                .map(reg -> studentManagementRepository.findByIdStudent(reg.getStudentId())
                        .orElseThrow(() -> new RuntimeException("Student not found for ID: " + reg.getStudentId())))
                .toList();
    }

    public List<ClassRegistration> getClassRegistrations(String classId) {
        return classRegistrationRepository.findByClassIdAndApproved(classId, true);
    }

    public String registerForClass(String studentId, String classId) {
        // Check if student is already in the waiting list
        if (classRegistrationRepository.findByStudentId(studentId)
                .stream()
                .anyMatch(registration -> registration.getClassId().equals(classId))) {
            return "Student already registered for this class!";
        }

        // Add student to waiting list
        ClassRegistration registration = ClassRegistration.builder()
                .classId(classId)
                .studentId(studentId)
                .approved(false) // Admin approval needed
                .build();
        classRegistrationRepository.save(registration);
        return "Student added to the waiting list!";
    }

    public void cancelClassRegistration(String studentId, String classId) {
        classRegistrationRepository.deleteById(classId);
    }
}

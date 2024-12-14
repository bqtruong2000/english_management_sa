package org.example.englishmanagement.repository;

import org.example.englishmanagement.model.Timetable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface TimetableRepository extends MongoRepository<Timetable, String> {
    Optional<Timetable> findByClassId(String classId);

    // Custom query to find classId and schedule by date
    @Aggregation(pipeline = {
            "{ '$unwind': '$schedule' }", // Unwind the schedule array
            "{ '$match': { 'schedule.date': ?0 } }", // Match the specified date
            "{ '$project': { 'classId': 1, 'schedule.startDate': 1, 'schedule.endDate': 1, '_id': 0 } }" // Project required fields
    })
    List<Map<String, Object>> findClassesByDate(String date);
}

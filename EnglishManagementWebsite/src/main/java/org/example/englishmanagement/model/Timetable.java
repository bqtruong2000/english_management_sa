package org.example.englishmanagement.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

@Document(collection = "timetables")
@Data
public class Timetable {
    @Id
    private String id;
    private String classId;
    private List<ScheduleItem> schedule;

    @Data
    public static class ScheduleItem {
        private String date;
        private String startDate;
        private String endDate;

        public ScheduleItem(String date, String startDate, String endDate) {
            this.date = date;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        public LocalTime getStartTime() {
            return LocalTime.parse(startDate);
        }

        public LocalTime getEndTime() {
            return LocalTime.parse(endDate);
        }
    }
}
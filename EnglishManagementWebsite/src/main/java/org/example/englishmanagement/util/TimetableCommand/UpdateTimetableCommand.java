package org.example.englishmanagement.util.TimetableCommand;
import org.example.englishmanagement.model.Timetable;
import org.example.englishmanagement.service.TimetableService;
import org.example.englishmanagement.util.Command;

import java.util.List;
// UpdateTimetableCommand.java
public class UpdateTimetableCommand implements Command {

    private TimetableService timetableService;
    private String classId;
    private List<Timetable.ScheduleItem> newSchedule;

    public UpdateTimetableCommand(TimetableService timetableService, String classId, List<Timetable.ScheduleItem> newSchedule) {
        this.timetableService = timetableService;
        this.classId = classId;
        this.newSchedule = newSchedule;
    }

    @Override
    public void execute() {
        timetableService.updateTimeTable(classId, newSchedule);
    }
}


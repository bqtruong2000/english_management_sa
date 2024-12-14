package org.example.englishmanagement.util.TimetableCommand;
import org.example.englishmanagement.model.Timetable;
import org.example.englishmanagement.service.TimetableService;
import org.example.englishmanagement.util.Command;

import java.util.List;
// DeleteTimetableCommand.java
public class DeleteTimetableCommand implements Command {

    private TimetableService timetableService;
    private String classId;

    public DeleteTimetableCommand(TimetableService timetableService, String classId) {
        this.timetableService = timetableService;
        this.classId = classId;
    }

    @Override
    public void execute() {
        timetableService.deleteTimeTable(classId);
    }
}


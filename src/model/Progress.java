package model;

import java.time.LocalDate;
import java.util.List;

public class Progress {

    private String userID;
    private String scheduleID;
    private List<LocalDate> sessionDates;
    private List<TimeRange> availableTimes;
    private List<ExercisesStatus> exercisesStatusList;

    public Progress(String userID, String scheduleID, List<LocalDate> sessionDates, List<TimeRange> availableTimes, List<ExercisesStatus> exercisesStatusList) {
        this.userID = userID;
        this.scheduleID = scheduleID;
        this.sessionDates = sessionDates;
        this.availableTimes = availableTimes;
        this.exercisesStatusList = exercisesStatusList;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public List<LocalDate> getSessionDates() {
        return sessionDates;
    }

    public void setSessionDates(List<LocalDate> sessionDates) {
        this.sessionDates = sessionDates;
    }

    public List<TimeRange> getAvailableTimes() {
        return availableTimes;
    }

    public void setAvailableTimes(List<TimeRange> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public List<ExercisesStatus> getExercisesStatusList() {
        return exercisesStatusList;
    }

    public void setExercisesStatusList(List<ExercisesStatus> exercisesStatusList) {
        this.exercisesStatusList = exercisesStatusList;
    }

}

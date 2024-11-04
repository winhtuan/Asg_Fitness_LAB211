package model;

import java.time.LocalDate;
import java.util.List;

public class Schedule {

    private String scheduleID;
    private String cusID;
    private String courseID;
    private String workoutID;
    private Boolean level;
    private LocalDate startDate;
    private List<LocalDate> sessionDate;
    private List<TimeRange> availableTimes;
    private List<List<String>> sessionExercises;

    public Schedule(String scheduleID, String cusID, String courseID, String workoutID, Boolean level, LocalDate startDate, List<LocalDate> sessionDate, List<TimeRange> availableTimes, List<List<String>> sessionExercises) {
        this.scheduleID = scheduleID;
        this.cusID = cusID;
        this.courseID = courseID;
        this.workoutID = workoutID;
        this.level = level;
        this.startDate = startDate;
        this.sessionDate = sessionDate;
        this.availableTimes = availableTimes;
        this.sessionExercises = sessionExercises;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Boolean getLevel() {
        return level;
    }

    public void setLevel(Boolean level) {
        this.level = level;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public String getCusID() {
        return cusID;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public List<TimeRange> getAvailableTimes() {
        return availableTimes;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public List<LocalDate> getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(List<LocalDate> sessionDate) {
        this.sessionDate = sessionDate;
    }

    public void setCusID(String cusID) {
        this.cusID = cusID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setAvailableTimes(List<TimeRange> availableTimes) {
        this.availableTimes = availableTimes;
    }

    public List<List<String>> getSessionExercises() {
        return sessionExercises;
    }

    public void setSessionExercises(List<List<String>> sessionExercises) {
        this.sessionExercises = sessionExercises;
    }

}

package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Course;
import model.ExercisesStatus;
import model.Progress;
import model.Schedule;
import model.TimeRange;
import model.Workout;
import reponsitory.ProgressRepository;
import reponsitory.ScheduleReponsitory;
import util.Validation;
import view.View;
import viewSwing.GetTimeForCourseRegisterDialog;

public class CourseRegistration {

    private CourseService courseService;
    private List<Schedule> schedules;
    private List<Progress> progresses;
    private Validation validation;
    private UserService userService;

    public CourseRegistration() {
        validation = new Validation();
        courseService = new CourseService();
        userService = new UserService();
        this.schedules = new ScheduleReponsitory().readFile();
        this.progresses = new ProgressRepository().readFile();
    }

    public Schedule registerCourse(String userID, String courseID, Boolean isBeginner, LocalDate startDate) {
        if (userService.findByID(userID) == null) {
            JOptionPane.showMessageDialog(null, "Invalid User ID! Can't found this ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        if (new CourseService().findByID(courseID) == null) {
            JOptionPane.showMessageDialog(null, "Invalid Course ID! Can't found this ID.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        if (!isCourseCompletedForUser(userID, courseID)) {
            JOptionPane.showMessageDialog(null, "You must complete all sessions of your current registration for course " + courseID + " before registering again.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        
        Course course = courseService.findByID(courseID);
        Workout selectedWorkout = new WorkoutService().findByID(course.getWorkoutID());

        int frequency = isBeginner ? 1 : 3;

        if (startDate.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Start date cannot be in the past. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        GetTimeForCourseRegisterDialog timeDialog = new GetTimeForCourseRegisterDialog(new JFrame(), frequency);
        List<TimeRange> availableTimes = timeDialog.showDialog();
        if (availableTimes == null || availableTimes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No available times were entered. Course registration cannot proceed.", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        List<LocalDate> sessionDates = generateSessionDates(startDate, course.getTotalWeek(), frequency);
        List<List<String>> sessionExercises = assignExercisesToSessions(selectedWorkout.getExercises(), sessionDates.size());

        displaySchedule(sessionDates, availableTimes, sessionExercises);
        String id = new ScheduleService().generateScheduleID(); // auto create Schedule ID
        // save schedule
        Schedule schedule = new Schedule(id, userID, courseID, selectedWorkout.getWorkoutID(), isBeginner, startDate, sessionDates, availableTimes, sessionExercises);
        schedules.add(schedule);
        new ScheduleReponsitory().writeFile(schedules);
        // save progress
        Progress progress = createProgress(userID, id, sessionExercises, sessionDates, availableTimes);
        progresses.add(progress);
        new ProgressRepository().writeFile(progresses);
        // end
        JOptionPane.showMessageDialog(null, "Congratulations Register course " + courseID + " successful.", "Notice", JOptionPane.INFORMATION_MESSAGE);
        return schedule;
    }

    private boolean isCourseCompletedForUser(String userID, String courseID) {
        Progress progress = new ProgressService().findProgressByUserID(userID);
        Schedule schedule = new ScheduleService().findScheduleByUserID(userID);

        if (progress == null || schedule == null) {
            return true;
        }

        if (progress.getUserID().equals(userID) && schedule.getCourseID().equals(courseID)) {
            if (progress.getExercisesStatusList().stream().anyMatch(new ProgressService()::isSessionIncomplete)) {
                return false;
            }
        }

        return true;
    }

    private List<TimeRange> gatherAvailableTimes(int frequency) {
        List<TimeRange> availableTimes = new ArrayList<>();
        for (int i = 0; i < frequency; i++) {
            LocalTime startTime = validation.getTime("Enter your available start time for day " + (i + 1) + " (HH:mm): ");
            double duration = validation.getValue("Enter duration in hours for this session: ", Double.class);
            LocalTime endTime = startTime.plusMinutes((long) (duration * 60));
            availableTimes.add(new TimeRange(startTime, endTime));
        }
        return availableTimes;
    }

    private List<LocalDate> generateSessionDates(LocalDate startDate, int totalWeeks, int frequency) {
        List<LocalDate> sessionDates = new ArrayList<>();
        LocalDate sessionDate = startDate;
        for (int week = 0; week < totalWeeks; week++) {
            for (int i = 0; i < frequency; i++) {
                sessionDates.add(sessionDate);
                sessionDate = sessionDate.plusDays(2);
            }
            sessionDate = startDate.plusWeeks(week + 1);
        }
        return sessionDates;
    }

    public List<List<String>> assignExercisesToSessions(List<String> exercises, int sessionCount) {
        List<List<String>> sessionExercises = new ArrayList<>();
        int totalExercises = exercises.size();
        int currentIndex = 0;

        for (int i = 0; i < sessionCount; i++) {
            List<String> currentSessionExercises = new ArrayList<>();
            for (int j = 0; j < 2; j++) {
                if (currentIndex < totalExercises) {
                    currentSessionExercises.add(exercises.get(currentIndex));
                    currentIndex++;
                } else {
                    currentIndex = 0;
                    currentSessionExercises.add(exercises.get(currentIndex));
                    currentIndex++;
                }
            }
            sessionExercises.add(currentSessionExercises);
        }
        return sessionExercises;
    }

    private Progress createProgress(String userID, String scheduleID, List<List<String>> sessionExercises, List<LocalDate> sessionDates, List<TimeRange> availableTimes) {
        List<ExercisesStatus> exercisesStatusList = new ArrayList<>();
        for (List<String> exercises : sessionExercises) {
            ExercisesStatus exercisesStatus = new ExercisesStatus();
            for (String exercise : exercises) {
                exercisesStatus.addExerciseStatus(exercise, false);
            }
            exercisesStatusList.add(exercisesStatus);
        }
        return new Progress(userID, scheduleID, sessionDates, availableTimes, exercisesStatusList);
    }

    public void displaySchedule(List<LocalDate> sessionDates, List<TimeRange> availableTimes, List<List<String>> sessionExercises) {
        System.out.println("Weekly Schedule:\n");
        int currentWeek = 1;
        for (int i = 0; i < sessionDates.size(); i++) {
            if (i % availableTimes.size() == 0 && i > 0) {
                currentWeek++;
            }
            if (i % availableTimes.size() == 0) {
                System.out.printf("Week %d:\n", currentWeek);
            }
            LocalDate date = sessionDates.get(i);
            TimeRange timeRange = availableTimes.get(i % availableTimes.size());
            List<String> currentSessionExercises = sessionExercises.get(i);

            System.out.printf("Session %d: %s | Start at %s, End at %s\n",
                    (i % availableTimes.size()) + 1,
                    date.format(View.DATE_FORMAT),
                    timeRange.getStart(),
                    timeRange.getEnd());

            System.out.println("Exercises:");
            currentSessionExercises.forEach(exercise -> System.out.println("- " + exercise));
        }
    }

}

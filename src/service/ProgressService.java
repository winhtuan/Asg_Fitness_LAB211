package service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import model.ExercisesStatus;
import model.Progress;
import model.Schedule;
import model.TimeRange;
import reponsitory.ProgressRepository;
import reponsitory.ScheduleReponsitory;
import util.Validation;
import view.View;

public class ProgressService implements IProgressService {

    private List<Progress> allProgresses;
    private Validation check;

    public ProgressService() {
        check = new Validation();
        allProgresses = new ProgressRepository().readFile();
    }

    @Override
    public void viewUserProgress(String userId) {
        List<Schedule> userSchedules = new ScheduleReponsitory().readFile();
        if (userSchedules == null || userSchedules.isEmpty()) {
            System.out.println("No schedule found for user " + userId + ".");
            return;
        }
        Schedule targetSchedule = new ScheduleService().findScheduleByUserID(userId);
        System.out.println("Progress Evaluation for user " + userId + ":");
        Progress userProgress = getUserProgress(userId, targetSchedule.getScheduleID());

        if (userProgress != null) {
            List<ExercisesStatus> exercisesStatusList = userProgress.getExercisesStatusList();
            int totalExercises = countTotalExercises(exercisesStatusList);
            int completedExercises = countCompletedExercises(exercisesStatusList);
            double progressPercentage = calculateProgressPercentage(totalExercises, completedExercises);

            System.out.println("Progress for User: " + userId + " in course " + targetSchedule.getCourseID());
            System.out.println("Total Exercises: " + totalExercises);
            System.out.println("Completed Exercises: " + completedExercises);
            System.out.printf("Completion: %.2f%%\n", progressPercentage);
        } else {
            System.out.println("No progress found for user " + userId + " in schedule " + targetSchedule.getScheduleID());
        }
    }

    public double calculateProgressPercentage(int totalExercises, int completedExercises) {
        return (totalExercises > 0) ? ((double) completedExercises / totalExercises) * 100 : 0;
    }

    public Progress getUserProgress(String userId, String courseID) {
        List<Progress> listUserProgresses = new ArrayList<>();
        ProgressRepository progressRepo = new ProgressRepository();

        for (Progress progress : progressRepo.readFile()) {
            if (progress.getUserID().equalsIgnoreCase(userId)) {
                listUserProgresses.add(progress);
            }
        }
        if (listUserProgresses.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No progress record found for user " + userId, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        boolean scheduleFound = false;
        for (Schedule schedule : new ScheduleReponsitory().readFile()) {
            for (Progress userProgress : listUserProgresses) {
                if (schedule.getCourseID().equalsIgnoreCase(courseID) && schedule.getScheduleID().equalsIgnoreCase(userProgress.getScheduleID())) {
                    scheduleFound = true;
                    return userProgress;
                }
            }
        }
        if (!scheduleFound) {
            JOptionPane.showMessageDialog(null, "No schedule found for course " + courseID, "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
        return null;
    }

    public int countTotalExercises(List<ExercisesStatus> exercisesStatusList) {
        int totalExercises = 0;
        for (ExercisesStatus exerciseStatus : exercisesStatusList) {
            totalExercises += exerciseStatus.getListExercise().size();
        }
        return totalExercises;
    }

    public int countCompletedExercises(List<ExercisesStatus> exercisesStatusList) {
        int completedExercises = 0;
        for (ExercisesStatus exerciseStatus : exercisesStatusList) {
            for (String exercise : exerciseStatus.getListExercise()) {
                if (Boolean.TRUE.equals(exerciseStatus.getStatus(exercise))) {
                    completedExercises++;
                }
            }
        }
        return completedExercises;
    }

    @Override
    public void updateUserProgress(String userId) {
        Schedule targetSchedule = new ScheduleService().findScheduleByUserID(userId);
        Progress userProgress = getUserProgress(userId, targetSchedule.getScheduleID());
        if (userProgress != null) {
            System.out.println("Available workout dates and statuses for user " + userId + ":");

            if (displayIncompleteSessions(userProgress)) {
                new ProgressRepository().writeFile(allProgresses);
                System.out.println("Progress updates saved successfully.");
            } else {
                System.out.println("All sessions are complete. No updates required.");
            }
        } else {
            System.out.println("No progress found for user " + userId + " in course " + targetSchedule.getScheduleID());
        }
    }

    public boolean displayIncompleteSessions(Progress userProgress) {
        List<LocalDate> sessionDates = userProgress.getSessionDates();
        List<ExercisesStatus> exercisesStatusList = userProgress.getExercisesStatusList();
        boolean hasIncompleteSessions = false;
        for (int i = 0; i < sessionDates.size(); i++) {
            if (isSessionIncomplete(exercisesStatusList.get(i))) {
                LocalDate date = sessionDates.get(i);
                displaySessionInfo(date, exercisesStatusList.get(i));
                System.out.println("Updating statuses for exercises on " + date.format(View.DATE_FORMAT) + ":");
                updateStatusForSession(userProgress, i, date);
                hasIncompleteSessions = true;
                break;
            }
        }
        return hasIncompleteSessions;
    }

    public boolean isSessionIncomplete(ExercisesStatus exerciseStatus) {
        return exerciseStatus.getExerciseStatusMap().values().stream().anyMatch(status -> !Boolean.TRUE.equals(status));
    }

    public void displaySessionInfo(LocalDate date, ExercisesStatus exerciseStatus) {
        System.out.println("Date: " + date.format(View.DATE_FORMAT));
        System.out.println("Exercises: " + String.join(", ", exerciseStatus.getListExercise()));
        System.out.println("Status: Not Complete");
    }

    public void updateStatusForSession(Progress userProgress, int dateIndex, LocalDate inputDate) {
        ExercisesStatus statusToUpdate = userProgress.getExercisesStatusList().get(dateIndex);
        Map<String, Boolean> statusMap = statusToUpdate.getExerciseStatusMap();
        for (Map.Entry<String, Boolean> entry : statusMap.entrySet()) {
            if (Boolean.FALSE.equals(entry.getValue())) {
                boolean newStatus = check.getBoolean("Enter status for exercise " + entry.getKey() + ": ");
                statusMap.put(entry.getKey(), newStatus);
            }
        }
        System.out.println("Status updated successfully for date " + inputDate.format(View.DATE_FORMAT) + ".");
    }

    public void updateExerciseStatus(Map<String, Boolean> statusMap, String exercise, boolean newStatus) {
        statusMap.put(exercise, newStatus);
    }

    @Override
    public void display() {
        for (Progress progress : allProgresses) {
            System.out.println("=".repeat(40));
            System.out.println("User ID: " + progress.getUserID());
            System.out.println("Schedule ID: " + progress.getScheduleID());
            System.out.println("-".repeat(15) + "Progress" + "-".repeat(15));

            List<LocalDate> sessionDates = progress.getSessionDates();
            List<TimeRange> availableTimes = progress.getAvailableTimes();
            List<ExercisesStatus> exercisesStatusList = progress.getExercisesStatusList();

            for (int i = 0; i < sessionDates.size(); i++) {
                System.out.printf("Session %d: %s, %s - %s\n",
                        i + 1,
                        sessionDates.get(i).format(View.DATE_FORMAT),
                        availableTimes.get(i).getStart(),
                        availableTimes.get(i).getEnd());

                ExercisesStatus sessionStatus = exercisesStatusList.get(i);

                System.out.print("Exercises: ");
                System.out.println(String.join(", ", sessionStatus.getListExercise()));

                System.out.print("Status: ");
                List<Boolean> statuses = new ArrayList<>();
                for (String exercise : sessionStatus.getListExercise()) {
                    statuses.add(sessionStatus.getStatus(exercise));
                    break;
                }
                System.out.println(statuses.stream().findFirst().orElse(false));
            }
        }
    }

    @Override
    public Progress findByID(String id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Progress findProgressByUserID(String userID) {
        Schedule targetSchedule = new ScheduleService().findScheduleByUserID(userID);
        for (Progress progress : allProgresses) {
            if (progress.getUserID().equals(userID) && progress.getScheduleID().equals(targetSchedule.getScheduleID())) {
                return progress;
            }
        }
        return null;
    }
}

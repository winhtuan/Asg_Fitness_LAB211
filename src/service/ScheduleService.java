package service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import model.ExercisesStatus;
import model.Progress;
import model.Schedule;
import model.TimeRange;
import reponsitory.ProgressRepository;
import reponsitory.ScheduleReponsitory;

public class ScheduleService implements Service<Schedule> {

    private List<Schedule> schedules;

    public ScheduleService() {
        schedules = new ScheduleReponsitory().readFile();
    }

    public List<Schedule> getAllSchedule() {
        return schedules;
    }

    public String generateScheduleID() {
        int counter = 1;
        Set<String> existingIds = getExistingIds();
        String newId;
        do {
            newId = String.format("SKD-%04d", counter++);
        } while (existingIds.contains(newId));

        return newId;
    }

    private Set<String> getExistingIds() {
        Set<String> ids = new HashSet<>();
        for (Schedule schedule : schedules) {
            ids.add(schedule.getScheduleID());
        }
        return ids;
    }

    public void update(Schedule e) {
        for (int i = 0; i < schedules.size(); i++) {
            if (schedules.get(i).getScheduleID().equalsIgnoreCase(e.getScheduleID())) {
                schedules.set(i, e);
                new ScheduleReponsitory().writeFile(schedules);
            }
        }
    }

    /**
     *
     * @param scheduleID
     * @param sessionDates list ngay tap
     * @param targetDate ngay muon doi
     * @param newDate ngay doi
     */
    public void editDate(String scheduleID, List<LocalDate> sessionDates, LocalDate targetDate, LocalDate newDate) {
        sessionDates.forEach(date -> System.out.println("- " + date.format(DATE_FORMAT)));

        if (sessionDates.contains(targetDate)) {
            int dateIndex = sessionDates.indexOf(targetDate);
            if (newDate.isBefore(LocalDate.now())) {
                JOptionPane.showMessageDialog(null, "Start date cannot be in the past. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sessionDates.set(dateIndex, newDate);
            new ScheduleReponsitory().writeFile(schedules);
            JOptionPane.showMessageDialog(null, "Date updated successfully from " + targetDate + " to " + newDate, "Notice", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "The specified date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     *
     * @param scheduleID
     * @param availableTimes list thoi gian tap luyen
     * @param sessionDates
     * @param targetDate ngay sua thoi gian
     * @param newStartTime thoi gian bat dau moi
     * @param duration thoi luong moi buoi tap
     */
    public void editTime(String scheduleID, List<TimeRange> availableTimes, List<LocalDate> sessionDates,
            LocalDate targetDate, LocalTime newStartTime, double duration) {
        if (targetDate.isBefore(LocalDate.now())) {
            JOptionPane.showMessageDialog(null, "Start date cannot be in the past. Please enter a valid date.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (sessionDates.contains(targetDate)) {
            int dateIndex = sessionDates.indexOf(targetDate);
            LocalTime newEndTime = newStartTime.plusMinutes((long) (duration * 60));
            availableTimes.set(dateIndex, new TimeRange(newStartTime, newEndTime));
            new ScheduleReponsitory().writeFile(schedules);
            JOptionPane.showMessageDialog(null, "Congratulations time updated successfully for session on " + targetDate.format(DATE_FORMAT), "Notice", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "The specified date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void notifySession(String userID, String courseID) {
        List<Progress> listUserProgresses = new ArrayList<>();
        ProgressRepository progressRepo = new ProgressRepository();

        for (Progress progress : progressRepo.readFile()) {
            if (progress.getUserID().equalsIgnoreCase(userID)) {
                listUserProgresses.add(progress);
            }
        }

        if (listUserProgresses.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No progress record found for user " + userID, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean scheduleFound = false;

        for (Schedule schedule : schedules) {
            for (Progress userProgress : listUserProgresses) {
                if (schedule.getCourseID().equalsIgnoreCase(courseID) && schedule.getScheduleID().equalsIgnoreCase(userProgress.getScheduleID())) {
                    scheduleFound = true;

                    List<LocalDate> sessionDates = userProgress.getSessionDates();
                    List<TimeRange> availableTimes = userProgress.getAvailableTimes();
                    List<ExercisesStatus> exercisesStatusList = userProgress.getExercisesStatusList();
                    LocalDate today = LocalDate.now();
                    boolean upcomingSessionFound = false;

                    upcomingSessionFound = notifyTodaySession(sessionDates, availableTimes, today);

                    if (!upcomingSessionFound) {
                        upcomingSessionFound = notifyNextSession(sessionDates, availableTimes, exercisesStatusList, today);
                    }

                    if (!upcomingSessionFound) {
                        JOptionPane.showMessageDialog(null, "No upcoming sessions found for course " + courseID, "Notice", JOptionPane.INFORMATION_MESSAGE);
                    }
                    return;
                }
            }
        }

        if (!scheduleFound) {
            JOptionPane.showMessageDialog(null, "No schedule found for course " + courseID, "Notice", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private boolean notifyTodaySession(List<LocalDate> sessionDates, List<TimeRange> availableTimes, LocalDate today) {
        for (int i = 0; i < sessionDates.size(); i++) {
            LocalDate sessionDate = sessionDates.get(i);
            if (sessionDate.isEqual(today)) {
                JOptionPane.showMessageDialog(null, "You have a session today on "
                        + sessionDate.format(DATE_FORMAT) + " at "
                        + availableTimes.get(i).getStart() + " - " + availableTimes.get(i).getEnd()
                        + ". Please make sure to attend!", "Today's Session", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    private boolean notifyNextSession(List<LocalDate> sessionDates, List<TimeRange> availableTimes,
            List<ExercisesStatus> exercisesStatusList, LocalDate today) {
        for (int i = 0; i < sessionDates.size(); i++) {
            LocalDate sessionDate = sessionDates.get(i);
            ExercisesStatus currentSessionStatus = exercisesStatusList.get(i);
            boolean isCompleted = currentSessionStatus.getExerciseStatusMap().values().stream().allMatch(Boolean::booleanValue);

            if (sessionDate.isAfter(today) && !isCompleted) {
                JOptionPane.showMessageDialog(null, "Your next session is on "
                        + sessionDate.format(DATE_FORMAT) + " at "
                        + availableTimes.get(i).getStart() + " - " + availableTimes.get(i).getEnd()
                        + ". Please complete your exercises: "
                        + String.join(", ", currentSessionStatus.getListExercise()),
                        "Upcoming Session", JOptionPane.INFORMATION_MESSAGE);
                return true;
            }
        }
        return false;
    }

    @Override
    public Schedule findByID(String id) {
        for (Schedule schedule : schedules) {
            if (schedule.getScheduleID().equalsIgnoreCase(id)) {
                return schedule;
            }
        }
        return null;
    }

    public Schedule findScheduleByUserID(String userID) {
        for (Schedule schedule : schedules) {
            if (schedule.getCusID().equalsIgnoreCase(userID)) {
                return schedule;
            }
        }
        System.out.println("No schedule found for user ID: " + userID);
        return null;
    }

    @Override
    public void display() {
        if (schedules == null || schedules.isEmpty()) {
            System.out.println("No schedules available to display.");
            return;
        }
        System.out.println("=".repeat(70));
        for (Schedule schedule : schedules) {
            System.out.printf("%-10s | %-10s | %-10s | %-10s | %s\n", "Schedule ID", "User ID", "Course ID", "Workout ID", "Level");
            System.out.println("-".repeat(70));
            System.out.printf("%-10s | %-10s | %-10s | %-10s | %s\n",
                    schedule.getScheduleID(),
                    schedule.getCusID(),
                    schedule.getCourseID(),
                    schedule.getWorkoutID(),
                    schedule.getLevel() ? "Beginner" : "Advance");

            if (schedule.getSessionDate().isEmpty()) {
                System.out.println("No sessions available for this schedule.");
                continue;
            }
            System.out.println("\nWeekly Schedule:");
            for (int i = 0; i < schedule.getSessionDate().size(); i++) {
                System.out.printf("Session %d: %s, Start at %s; End at %s\n",
                        i + 1,
                        schedule.getSessionDate().get(i),
                        schedule.getAvailableTimes().get(i).getStart(),
                        schedule.getAvailableTimes().get(i).getEnd());
                System.out.println("Exercises: " + String.join(", ", schedule.getSessionExercises().get(i)));
            }
            System.out.println("=".repeat(70));
        }
    }
}

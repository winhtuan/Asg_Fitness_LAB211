package reponsitory;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Schedule;
import model.TimeRange;
import util.Validation;

public class ScheduleReponsitory implements IScheduleReponsitory {

    private Validation validation;
    private final String PATH = new File("src\\data\\schedule.csv").getAbsolutePath();

    public ScheduleReponsitory() {
        validation = new Validation();
    }

    @Override
    public List<Schedule> readFile() {
        List<Schedule> schedules = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                String scheduleID = data[0].trim();
                String cusID = data[1].trim();
                String courseID = data[2].trim();
                String workoutID = data[3].trim();
                Boolean level = Boolean.valueOf(data[4].trim());
                LocalDate startDate = validation.convertStringToLocalDate(data[5].trim());

                List<LocalDate> sessionDates = new ArrayList<>();
                List<TimeRange> availableTimes = new ArrayList<>();
                List<List<String>> sessionExercises = new ArrayList<>();

                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) {
                        break;
                    }
                    if (line.startsWith("Session")) {
                        String[] sessionData = line.split(", ");
                        String[] dateParts = sessionData[0].split(":");
                        LocalDate sessionDate = validation.convertStringToLocalDate(dateParts[1].trim());

                        String[] timeParts = sessionData[1].split(";");
                        LocalTime startTime = validation.convertStringToLocalTime(timeParts[0].replace("Start at ", "").trim());
                        LocalTime endTime = validation.convertStringToLocalTime(timeParts[1].replace("End at ", "").trim());

                        line = br.readLine();
                        if (line != null && line.startsWith("Exercises:")) {
                            String[] exerciseParts = line.split(":");
                            List<String> exercises = Arrays.asList(exerciseParts[1].trim().split(","));

                            sessionDates.add(sessionDate);
                            availableTimes.add(new TimeRange(startTime, endTime));
                            sessionExercises.add(exercises);
                        } else {
                            System.out.println("Expected exercises line but found: " + line);
                        }
                    }
                }
                Schedule schedule = new Schedule(scheduleID, cusID, courseID, workoutID, level, startDate, sessionDates, availableTimes, sessionExercises);
                schedules.add(schedule);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return schedules;
    }

    @Override
    public void writeFile(List<Schedule> schedules) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(PATH))) {
            for (Schedule schedule : schedules) {
                bf.write(schedule.getScheduleID() + "," + schedule.getCusID() + "," + schedule.getCourseID() + ","
                        + schedule.getWorkoutID() + "," + schedule.getLevel() + ","
                        + schedule.getStartDate().format(DATE_FORMAT) + "\n");
                bf.write("Weekly Schedule\n");

                List<LocalDate> sessionDates = schedule.getSessionDate();
                List<TimeRange> availableTimes = schedule.getAvailableTimes();
                List<List<String>> sessionExercises = schedule.getSessionExercises();

                for (int i = 0; i < sessionDates.size(); i++) {
                    LocalDate date = sessionDates.get(i);
                    bf.write("Session " + (i + 1) + ": " + date.format(DATE_FORMAT));
                    TimeRange timeRange = availableTimes.get(i % availableTimes.size());
                    bf.write(", Start at " + timeRange.getStart().format(TIME_FORMAT));
                    bf.write("; End at " + timeRange.getEnd().format(TIME_FORMAT) + "\n");
                    List<String> exercises = sessionExercises.get(i);
                    bf.write("Exercises: " + String.join(",", exercises) + "\n");
                }
                bf.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
    }
}

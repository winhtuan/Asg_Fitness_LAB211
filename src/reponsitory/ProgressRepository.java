package reponsitory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.ExercisesStatus;
import model.Progress;
import model.TimeRange;
import util.Validation;

public class ProgressRepository implements IProgressReponsitory {

    String PATH = new File("src\\data\\progress.csv").getAbsolutePath();
    private Validation check;

    public ProgressRepository() {
        check = new Validation();
    }

    @Override
    public List<Progress> readFile() {
        List<Progress> progresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",");
                String userID = data[0].trim();
                String scheduleID = data[1].trim();

                List<LocalDate> sessionDates = new ArrayList<>();
                List<TimeRange> availableTimes = new ArrayList<>();
                List<Map<String, Boolean>> exerciseStatusList = new ArrayList<>();

                Map<String, Boolean> exercisesStatusMap = null;

                while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    if (line.startsWith("Session")) {
                        exercisesStatusMap = new HashMap<>();
                        exerciseStatusList.add(exercisesStatusMap);

                        String[] sessionParts = line.split(",", 2);
                        String[] dateTimeParts = sessionParts[1].split(";");

                        LocalDate sessionDate = check.convertStringToLocalDate(sessionParts[0].split(":")[1].trim());
                        LocalTime startTime = check.convertStringToLocalTime(dateTimeParts[0].trim());
                        LocalTime endTime = check.convertStringToLocalTime(dateTimeParts[1].trim());

                        sessionDates.add(sessionDate);
                        availableTimes.add(new TimeRange(startTime, endTime));
                    } else if (line.startsWith("Exercises")) {
                        String[] exerciseParts = line.split(":");
                        if (exerciseParts.length > 1 && exercisesStatusMap != null) {
                            String[] exerciseList = exerciseParts[1].split(",");
                            for (String exercise : exerciseList) {
                                exercisesStatusMap.put(exercise.trim(), null);
                            }
                        }
                    } else if (line.startsWith("Status")) {
                        String[] statusParts = line.split(":");
                        if (statusParts.length > 1 && exercisesStatusMap != null) {
                            String[] statusList = statusParts[1].split(",");
                            int index = 0;
                            for (String exercise : exercisesStatusMap.keySet()) {
                                if (index < statusList.length) {
                                    exercisesStatusMap.put(exercise, Boolean.valueOf(statusList[index].trim()));
                                    index++;
                                }
                            }
                        }
                    }
                }

                List<ExercisesStatus> exercisesStatusObjects = new ArrayList<>();
                for (Map<String, Boolean> exerciseStatus : exerciseStatusList) {
                    exercisesStatusObjects.add(new ExercisesStatus(exerciseStatus));
                }

                Progress progress = new Progress(userID, scheduleID, sessionDates, availableTimes, exercisesStatusObjects);
                progresses.add(progress);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return progresses;
    }

    @Override
    public void writeFile(List<Progress> progresses) {
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(PATH))) {
            for (Progress progress : progresses) {
                bf.write(progress.getUserID() + ",");
                bf.write(progress.getScheduleID() + "\n");

                List<LocalDate> sessionDates = progress.getSessionDates();
                List<TimeRange> availableTimes = progress.getAvailableTimes();
                List<ExercisesStatus> exercisesStatusList = progress.getExercisesStatusList();

                for (int i = 0; i < sessionDates.size(); i++) {
                    LocalDate date = sessionDates.get(i);
                    bf.write("Session " + (i + 1) + ":" + date.format(DATE_FORMAT));

                    TimeRange timeRange = availableTimes.get(i % availableTimes.size());
                    bf.write("," + timeRange.getStart().format(TIME_FORMAT));
                    bf.write(";" + timeRange.getEnd().format(TIME_FORMAT) + "\n");

                    ExercisesStatus sessionStatus = exercisesStatusList.get(i);
                    bf.write("Exercises: " + String.join(",", sessionStatus.getListExercise()) + "\n");

                    bf.write("Status: ");
                    List<String> statuses = new ArrayList<>();
                    for (String exercise : sessionStatus.getListExercise()) {
                        statuses.add(sessionStatus.getStatus(exercise).toString());
                    }
                    bf.write(String.join(", ", statuses) + "\n");
                }
                bf.write("\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }

}

package reponsitory;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Workout;

public class WorkoutRepository implements IWorkoutResponsitory {

    private final String PATH = new File("src\\data\\workout.csv").getAbsolutePath();
    private List<Workout> workouts;

    public WorkoutRepository() {
        workouts = new ArrayList<>();
    }

    @Override
    public List<Workout> readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",", 3);
                if (parts.length == 3) {
                    String workoutID = parts[0];
                    String workoutName = parts[1];
                    List<String> exercises = Arrays.asList(parts[2].split(";"));
                    Workout workout = new Workout(workoutID, workoutName, exercises);
                    workouts.add(workout);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return workouts;
    }

    @Override
    public void writeFile(List<Workout> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Workout workout : entities) {
                String line = workout.getWorkoutID() + "," + workout.getWorkoutName() + "," + String.join(";", workout.getExercises());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }
}

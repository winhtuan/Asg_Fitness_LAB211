package model;

import java.util.List;

public class Workout {
    private String workoutID;
    private String workoutName;
    private List<String> exercises;
    public Workout(String workoutID, String workoutName, List<String> exercises) {
        this.workoutID = workoutID;
        this.workoutName = workoutName;
        this.exercises = exercises;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public List<String> getExercises() {
        return exercises;
    }
    
    
}

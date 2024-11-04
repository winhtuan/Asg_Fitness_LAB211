package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExercisesStatus {

    private Map<String, Boolean> exerciseStatusMap;

    public ExercisesStatus() {
        this.exerciseStatusMap = new HashMap<>();
    }

    public ExercisesStatus(Map<String, Boolean> exerciseStatusMap) {
        this.exerciseStatusMap = exerciseStatusMap;
    }

    public void addExerciseStatus(String exercise, Boolean status) {
        this.exerciseStatusMap.put(exercise, status);
    }

    public Map<String, Boolean> getExerciseStatusMap() {
        return exerciseStatusMap;
    }
    
    public Boolean getStatus(String exercise) {
        return exerciseStatusMap.getOrDefault(exercise, false);
    }

    public List<String> getListExercise() {
        return new ArrayList<>(exerciseStatusMap.keySet());
    }
    
}

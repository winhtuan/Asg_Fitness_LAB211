package service;

import java.util.List;
import model.Workout;
import reponsitory.WorkoutRepository;

public class WorkoutService implements IWorkoutService {

    List<Workout> listWorkOut;

    public WorkoutService() {
        listWorkOut = new WorkoutRepository().readFile();
    }

    public List<Workout> getListWorkOut() {
        return listWorkOut;
    }

    @Override
    public void createWorkOut(Workout e) {
        listWorkOut.add(e);
        new WorkoutRepository().writeFile(listWorkOut);
    }

    @Override
    public void display() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Workout findByID(String id) {
        return listWorkOut.stream().filter(p -> p.getWorkoutID().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    @Override
    public void update(Workout e) {
        for (int i = 0; i < listWorkOut.size(); i++) {
            if (listWorkOut.get(i).getWorkoutID().equalsIgnoreCase(e.getWorkoutID())) {
                listWorkOut.set(i, e);
                new WorkoutRepository().writeFile(listWorkOut);
            }
        }
    }

    @Override
    public void delete(String e) {
        listWorkOut.removeIf(p -> p.getWorkoutID().equalsIgnoreCase(e));
        new WorkoutRepository().writeFile(listWorkOut);
    }

}

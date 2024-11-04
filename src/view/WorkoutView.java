package view;

import java.util.ArrayList;
import java.util.List;
import model.Course;
import model.Workout;
import service.CourseService;
import service.WorkoutService;
import util.Validation;

public class WorkoutView implements IWorkoutView {

    private WorkoutService workout;
    private Validation input;
    private CourseView courseView;
    private CourseService courseService;

    public WorkoutView() {
        workout = new WorkoutService();
        input = new Validation();
        courseView = new CourseView();
        courseService = new CourseService();
    }

    public void createWorkout() {
        String workoutID;
        while (true) {
            workoutID = input.getInputString("Enter workout ID (format: WRK-YYYY):", WORKOUT_REGEX, "Invalid workout ID. Format must be WRK-YYYY where Y is a digit.");
            if (workout.findByID(workoutID) == null) {
                String workoutName;
                workoutName = input.getInputString("Enter workout name :", TEXT_REGEX, "Invalid Workout name. Only letters and spaces are accepted.");
                List<String> ex = new ArrayList<>();
                String exercises;
                do {
                    exercises = input.getInputString("Enter exercises  "  + " :", TEXT_REGEX, "Invalid Workout name. Only letters and spaces are accepted.");
                    ex.add(exercises);
                } while (input.continueConfirm("You want to add exercises : "));
                workout.createWorkOut(new Workout(workoutID, workoutName, ex));
                System.out.println("add sucessfully !!");
            }
        }
    }

    public void AddExercises() {
        courseView.display();
        String courseID;
        while (true) {
            courseID = input.getInputString("Enter course ID (format: COR-YYYY): ", COURSE_REGEX, "Invalid Course ID. Format must be COR-YYYY where Y is a digit. ");
            Course course = courseService.findByID(courseID);
            if (course == null) {
                System.out.println("Course not found! Please create the course first.");
            } else {
                System.out.println("Adding details for course: " + course.getCourseId() + " - " + course.getCourseName());
                Workout workoutID = workout.findByID(course.getWorkoutID());
                if(workoutID != null){
                    String exercises;
                    do {
                        exercises = input.getInputString("Enter exercises : " , TEXT_REGEX, "Invalid Workout name. Only letters and spaces are accepted.");
                        workoutID.getExercises().add(exercises);
                    } while (input.continueConfirm("You want to add exercises : "));
                }
                System.out.println("All exercises added to workout: " + course.getWorkoutID());
                break;
            }
        }
    }

    @Override
    public void display() {
        System.out.println("List All Workout :");
        System.out.printf("%-10s | %-30s | %-40s\n", "Workout ID", "Workout Name", "Exercises");
        System.out.println("-".repeat(60));
        for (Workout x : workout.getListWorkOut()) {
            System.out.printf("%-10s | %-30s | %-40s\n",
                    x.getWorkoutID(),
                    x.getWorkoutName(),
                    x.getExercises());
        }
        System.out.println("-".repeat(60));
    }

}

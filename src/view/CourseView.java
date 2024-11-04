package view;

import java.util.List;
import model.Coach;
import model.Course;
import model.Workout;
import service.CoachService;
import service.CourseService;
import service.NutritionService;
import service.WorkoutService;
import util.Validation;

public class CourseView implements ICourseView {

    private Validation check;
    private CourseService service;
    private CoachService coachService;
    private WorkoutService workoutService;
    private NutritionService nutritionService;

    public CourseView() {
        check = new Validation();
        service = new CourseService();
        coachService = new CoachService();
        workoutService = new WorkoutService();
        nutritionService = new NutritionService();
    }

    public static void main(String[] args) {
        new CourseView().createCourse();
    }

    public void createCourse() {
        System.out.println("Creating a new course");
        String courseId;
        while (true) {
            courseId = check.getInputString("Enter course ID (format: COR-YYYY): ", COURSE_REGEX, "Invalid Course ID. Format must be COR-YYYY where Y is a digit. ");
            if (service.findByID(courseId) == null) {
                break;
            } else {
                System.out.println("Course ID exsit !!");
            }
        }
        String courseName;
        courseName = check.getInputString("Enter course name : ", TEXT_REGEX, "Invalid course name. Only letters and spaces are accepted.");
        String coachID;
        while (true) {
            coachID = check.getInputString("Enter coach ID (format: COA-YYYY):", COACH_REGEX, "Invalid coach ID. Format must be COA-YYYY where Y is a digit. ");
            if (coachService.findByID(coachID) != null) {
                break;
            } else {
                System.out.println("CoachID not found !!");
            }
        }
        String workoutID;
        while (true) {
            workoutID = check.getInputString("Enter workout ID (format: WRK-YYYY):", WORKOUT_REGEX, "Invalid workout ID. Format must be WRK-YYYY where Y is a digit. ");
            Workout x = workoutService.findByID(workoutID);
            if (x != null) {
                break;
            } else {
                System.out.println("WorkoutID not found !!");
            }
        }
        String nutritionID;
        while (true) {
            nutritionID = check.getInputString("Enter nutrition ID (format: NUT-YYYY):", NUTITION_REGEX, "Invalid nutrition ID. Format must be NUT-YYYY where Y is a digit. ");
            if (nutritionService.findByID(nutritionID) != null) {
                break;
            } else {
                System.out.println("NutritionID not found !!");
            }
        }
        int totalWeek ;
        totalWeek = check.getValue("Enter total week :", Integer.class);
        double price;
        price = check.getValue("Enter price : ",Double.class);

        String description;
        description = check.getInputString("Enter description : ", TEXTNORMAL_REGEX, "Invalid description. Only letters and spaces are accepted.");
        service.createCourse(new Course(courseId, courseName, coachID, workoutID, nutritionID, price, totalWeek, description));
        System.out.println("Add sucessfully !!");
    }

    @Override
    public void display() {
        System.out.println("List All Courses:");
        System.out.printf("%-10s | %-15s | %-30s | %-15s | %-20s\n", "Course ID", "Coach Name", "Course Name", "Price", "Description");
        System.out.println("-".repeat(130));
        for (Course course : service.getListCourse()) {
            Coach coach = new CoachService().findByID(course.getCoachID());
            System.out.printf("%-10s | %-15s | %-30s | $%-15.2f | %-20s\n",
                    course.getCourseId(),
                    coach.getName(),
                    course.getCourseName(),
                    course.getPrice(),
                    course.getDescription());
        }
        System.out.println("-".repeat(130));
    }

    public void displayUserCourses(String userId) {
        List<Course> userCourses = service.getListRegistering().get(userId);
        if (userCourses == null || userCourses.isEmpty()) {
            System.out.println("User " + userId + " has not registered for any courses.");
            return;
        }
        System.out.println("Courses registered by user " + userId + ":");
        for (Course course : userCourses) {
            Coach coach = new CoachService().findByID(course.getCoachID());
            System.out.printf("%-10s | %-30s |  %-30s | $%-15.2f | %-20s\n",
                    course.getCourseId(),
                    course.getCourseName(),
                    coach.getName(),
                    course.getPrice(),
                    course.getDescription());
        }
    }

}

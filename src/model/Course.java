package model;

public class Course {

    private String courseId;
    private String courseName;
    private String coachID;
    private String workoutID;
    private String nutritionID;
    private double price;
    private int totalWeek;
    private String description;

    public Course(String courseId, String courseName, String coachID, String workoutID, String nutritionID, double price, int totalWeek, String description) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.coachID = coachID;
        this.workoutID = workoutID;
        this.nutritionID = nutritionID;
        this.price = price;
        this.totalWeek = totalWeek;
        this.description = description;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCoachID() {
        return coachID;
    }

    public String getWorkoutID() {
        return workoutID;
    }

    public String getNutritionID() {
        return nutritionID;
    }

    public double getPrice() {
        return price;
    }

    public int getTotalWeek() {
        return totalWeek;
    }

    public String getDescription() {
        return description;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }

    public void setWorkoutID(String workoutID) {
        this.workoutID = workoutID;
    }

    public void setNutritionID(String nutritionID) {
        this.nutritionID = nutritionID;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setTotalWeek(int totalWeek) {
        this.totalWeek = totalWeek;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

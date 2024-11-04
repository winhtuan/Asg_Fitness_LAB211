package reponsitory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import model.Course;

public class CourseRepository implements ICourseReponsitory {

    private List<Course> courses;
    private final String PATH = new File("src\\data\\course.csv").getAbsolutePath();

    public CourseRepository() {
        courses = new ArrayList<>();
    }
    
    @Override
    public List<Course> readFile() {
        try (BufferedReader br = new BufferedReader(new FileReader(PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String courseId = parts[0];
                    String courseName = parts[1];
                    String coachID = parts[2];
                    String workoutID = parts[3];
                    String nutritionID = parts[4];
                    double price = Double.parseDouble(parts[5]);
                    int totalWeek = Integer.parseInt(parts[6]);
                    String description = parts[7];
                    
                    Course course = new Course(courseId, courseName, coachID, workoutID, nutritionID, price, totalWeek, description);
                    courses.add(course);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        } catch (IOException ex) {
            System.out.println("Error reading file: " + PATH + ". Please check if the file is accessible.");
        }
        return courses;
    }

    @Override
    public void writeFile(List<Course> entities) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PATH))) {
            for (Course course : entities) {
                String line = String.join(",",
                        course.getCourseId(),
                        course.getCourseName(),
                        course.getCoachID(),
                        course.getWorkoutID(),
                        course.getNutritionID(),
                        String.valueOf(course.getPrice()),
                        String.valueOf(course.getTotalWeek()),
                        course.getDescription()
                );
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + PATH + ". Please check if the file is accessible.");
        }
    }
}

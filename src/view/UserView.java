package view;

import java.time.LocalDate;
import model.Address;
import model.Course;
import model.Users;
import service.CourseService;
import service.UserService;
import util.Validation;

public class UserView implements IUserView {

    private Validation check;
    private UserService userService;
    private CourseService courseService;
    private CourseView courseView;

    public UserView() {
        check = new Validation();
        userService = new UserService();
        courseService = new CourseService();
        courseView = new CourseView();
    }

    public void register() {
        String userID;
        while (true) {
            userID = check.getInputString("Enter User ID: ", USER_REGEX, "User ID");
            if (userService.findByID(userID) != null) {
                break;
            } else {
                System.out.println("userID not found !!");
            }
        }
        courseView.display();
        String courseID;
        Course course;
        while (true) {
            courseID = check.getInputString("Enter the course ID to register: ", COURSE_REGEX, "Course ID");
            course = courseService.findByID(courseID);
            if (course != null) {
                break;
            } else {
                System.out.println("courseID not found !!");
            }
        }

    }

    public void acessExercise() {

    }

    public Users registNewUser() {
        String id = check.getInputString("Enter ID: ", View.USER_REGEX, "User ID");
        String name = check.getInputString("Enter Your Name: ", View.TEXT_REGEX, "Name");
        String cmnd = check.getInputString("Enter Your SSN: ", View.SSN_REGEX, "SSN");
        String phoneNumber = check.getInputString("Enter Your Phone Number: ", View.PHONE_REGEX, "Phone Number");
        boolean sex = check.getBoolean("Enter sex (true for male, false for female): ");
        LocalDate birthday = check.getDate("Enter Your Birthday: ");
        String email = check.getInputString("Enter Your Email: ", View.EMAIL_REGEX, "Email");
        String country = check.getInputString("Enter Your Country: ", View.TEXT_REGEX, "Country");
        String city = check.getInputString("Enter Your City: ", View.TEXT_REGEX, "City");
        String street = check.getInputString("Enter Your Street: ", View.TEXT_REGEX, "Street");
        Address address = new Address(country, city, street);
        double height = check.getValue("Enter your height(m): ", Double.class);
        double weight = check.getValue("Enter your weight(kg): ", Double.class);
        Users newUser = new Users(id, name, cmnd, phoneNumber, sex, birthday, email, address, height, weight);
        userService.addNewCustomer(newUser);
        System.out.println("User registered: " + newUser);
        return newUser;
    }

    @Override
    public void display() {
        System.out.printf("|%-10s|%-25s|%-15s|%-12s|%-8s|%-25s|%-15s|%-36s|%-8s|%-8s|\n",
                "User ID", "User Name", "SSN", "Phone", "Gender", "Email", "Birthday", "Address", "Height", "Weight");
        for (Users users : userService.getAllUsers()) {
            System.out.println(users);
        }
    }

}

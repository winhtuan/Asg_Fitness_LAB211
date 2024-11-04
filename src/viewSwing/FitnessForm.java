package viewSwing;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.Address;
import model.Coach;
import model.Course;
import model.Nutrition;
import model.Schedule;
import model.TimeRange;
import model.Users;
import model.Workout;
import service.CoachService;
import service.CourseService;
import service.LoginService;
import service.NutritionService;
import service.ScheduleService;
import service.UserService;
import service.WorkoutService;
import util.Validation;
import view.View;

public class FitnessForm extends javax.swing.JFrame {

    private Validation validation;
    private boolean idHasError = false;
    private boolean workoutidHasError = false;
    private boolean nutritionidHasError = false;
    private boolean coachidHasError = false;
    private boolean nameHasError = false;
    private boolean emailHasError = false;
    private boolean phoneHasError = false;
    private boolean ssnHasError = false;
    private boolean countryHasError = false;
    private DefaultTableModel model;
    private DefaultTableModel modelUser;
    private DefaultTableModel modelCourse;
    private DefaultTableModel modelSchedule;
    private DefaultTableModel modelWorkout;
    private DefaultTableModel modelNutrition;
    private int pos = 0;
    private CoachService coachService = new CoachService();
    private UserService userService = new UserService();
    private CourseService courseService = new CourseService();
    private ScheduleService scheduleService = new ScheduleService();

    public FitnessForm() {
        validation = new Validation();
        initComponents();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        isValidation();
        isCustomer();
        isCourse();
        isWorkout();
        setLocationRelativeTo(null);
        model = (DefaultTableModel) tableCoach.getModel();
        modelUser = (DefaultTableModel) tableUser.getModel();
        modelCourse = (DefaultTableModel) tableCourse.getModel();
        modelSchedule = (DefaultTableModel) tableSchedule.getModel();
        modelWorkout = (DefaultTableModel) tableWorkout.getModel();
        modelNutrition = (DefaultTableModel) tableNutrition.getModel();
        labelMember();
        labelCoach();
        labelTotal();
        labelName();
    }
    public void labelName() {
        Coach c = new CoachService().findByID(new Login().getUserID());
        labelName.setText(c.getName());
    }

    public void labelMember() {
        int count = userService.getAllUsers().size();
        labelMember.setText("" + count);
    }

    public void labelCoach() {
        int count = coachService.getAllCoach().size();
        labelCoach.setText("" + count);
    }

    public void labelTotal() {
        double total = 0;
        for (int i = 0; i < scheduleService.getAllSchedule().size(); i++) {
            if (courseService.findByID(scheduleService.getAllSchedule().get(i).getCourseID()) != null) {
                total += courseService.findByID(scheduleService.getAllSchedule().get(i).getCourseID()).getPrice();
            }
        }
        labelTotal.setText("" + total);
    }

    public void viewCoach() {
        Coach coach = new CoachService().getAllCoach().get(pos);
        this.txtCoachID.setText(coach.getId());
        this.txtName.setText(coach.getName());
        this.txtEmail.setText(coach.getEmail());
        this.txtSSN.setText(coach.getCmnd());
        this.txtPhone.setText(coach.getPhoneNumber());

        Address address = coach.getAddress();
        if (address != null) {
            this.txtAddress.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getCountry());
        }

        if (coach.getBirthday() != null) {
            this.getDay.setSelectedItem(String.valueOf(coach.getBirthday().getDayOfMonth()));
            this.getMonth.setSelectedItem(String.valueOf(coach.getBirthday().getMonthValue()));
            this.getYear.setSelectedItem(String.valueOf(coach.getBirthday().getYear()));
        }
        this.getMale.setSelected(coach.isSex());
        this.getFemale.setSelected(!coach.isSex());

        this.levelbox.setSelectedItem(coach.getSpecialization());
        this.txtSalary.setText(String.format("%.2f", coach.getSalary()));
    }

    public void viewUser() {
        Users user = userService.getAllUsers().get(pos);

        this.txtUserID.setText(user.getId());
        this.txtUserName.setText(user.getName());
        this.txtUserEmail.setText(user.getEmail());
        this.txtUserSSN.setText(user.getCmnd());
        this.txtUserPhone.setText(user.getPhoneNumber());

        Address address = user.getAddress();
        if (address != null) {
            this.txtUserAddress.setText(address.getStreet() + ", " + address.getCity() + ", " + address.getCountry());
        }

        if (user.getBirthday() != null) {
            this.getDay1.setSelectedItem(String.valueOf(user.getBirthday().getDayOfMonth()));
            this.getMonth1.setSelectedItem(String.valueOf(user.getBirthday().getMonthValue()));
            this.getYear1.setSelectedItem(String.valueOf(user.getBirthday().getYear()));
        }
        this.getMale1.setSelected(user.isSex());
        this.getFemale1.setSelected(!user.isSex());
        this.txtheight.setText(String.format("%.2f", user.getHeight()));
        this.txtWeight.setText(String.format("%.2f", user.getWeight()));
    }

    public void viewCourse() {
        Course course = courseService.getListCourse().get(pos);
        this.txtCourseID.setText(course.getCourseId());
        this.txtCourseName.setText(course.getCourseName());
        this.txtCoachID1.setText(course.getCoachID());
        this.txtWorkOutID.setText(course.getWorkoutID());
        this.txtNutritionID.setText(course.getNutritionID());
        this.txtDescription.setText(course.getDescription());
        this.txtTotalWeek.setText(String.format("%d", course.getTotalWeek()));
        this.txtPrice.setText(String.format("%.2f", course.getPrice()));
    }

    public void viewCoachTable() {
        model.setNumRows(0);
        for (Coach x : new CoachService().getAllCoach()) {
            model.addRow(new Object[]{x.getId(), x.getName(), x.getPhoneNumber(), x.getEmail(), x.getCmnd(), x.getBirthday(), (x.isSex() ? "Male" : "Female"), x.getAddress(), x.getSalary(), x.getSpecialization()});
        }
    }

    public void viewWorkoutTable() {
        modelWorkout.setRowCount(0);
        for (Workout workout : new WorkoutService().getListWorkOut()) {
            Object[] rowData = {
                workout.getWorkoutID(),
                workout.getWorkoutName(),
                String.join(", ", workout.getExercises())
            };
            modelWorkout.addRow(rowData);
        }
    }

    public void viewNutritionTable() {
        modelNutrition.setRowCount(0);
        for (Nutrition nutrition : new NutritionService().getAllNutrition()) {
            Object[] rowData = {
                nutrition.getNutritionID(),
                String.join(", ", nutrition.getNutriList())
            };
            modelNutrition.addRow(rowData);
        }

    }

    public void viewCourseTable() {
        modelCourse.setRowCount(0);

        List<Course> courses = courseService.getListCourse();
        for (Course course : courses) {
            Object[] row = {
                course.getCourseId(),
                course.getCourseName(),
                course.getCoachID(),
                course.getWorkoutID(),
                course.getNutritionID(),
                course.getPrice(),
                course.getTotalWeek(),
                course.getDescription()
            };
            modelCourse.addRow(row);
        }
    }

    public void viewScheduleTable() {
        modelSchedule.setNumRows(0);
        for (Schedule schedule : scheduleService.getAllSchedule()) {
            String level = schedule.getLevel() ? "Beginner" : "Advance";
            for (int i = 0; i < schedule.getSessionDate().size(); i++) {
                Object[] rowData = {
                    schedule.getScheduleID(),
                    schedule.getCusID(),
                    schedule.getCourseID(),
                    schedule.getWorkoutID(),
                    level,
                    schedule.getSessionDate().get(i),
                    schedule.getAvailableTimes().get(i).getStart(),
                    schedule.getAvailableTimes().get(i).getEnd(),
                    String.join(", ", schedule.getSessionExercises().get(i))
                };
                modelSchedule.addRow(rowData);
            }
        }

    }

    public void viewUserTable() {
        modelUser.setNumRows(0);
        for (Users x : userService.getAllUsers()) {
            modelUser.addRow(new Object[]{x.getId(), x.getName(), x.getPhoneNumber(), x.getEmail(), x.getCmnd(), x.getBirthday(), (x.isSex() ? "Male" : "Female"), x.getAddress(), x.getHeight(), x.getWeight()});
        }
    }

    public final boolean isCustomer() {
        boolean isValid = true;
        txtUserID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtUserID.getText().trim();
                if (registerID != null && !registerID.isEmpty()) {
                    if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.USER_REGEX) != null) {
                        idHasError = false;
                    } else {
                        idHasError = true;
                        JOptionPane.showMessageDialog(null, "Please input format CUS-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCoachID.requestFocus();
                    }
                } else {
                    idHasError = true;
                    JOptionPane.showMessageDialog(null, "ID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    txtUserID.requestFocus();
                }
            }

        });
        txtUserName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String name = txtUserName.getText().trim();
                if (idHasError == false) {
                    if (name != null && !name.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, name, View.TEXT_REGEX) != null) {
                            nameHasError = false;
                        } else {
                            nameHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input capitalize the first letter !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtUserName.requestFocus();
                        }
                    } else {
                        nameHasError = true;
                        JOptionPane.showMessageDialog(null, "Name can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUserName.requestFocus();
                    }
                }
            }
        });
        txtUserSSN.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String ssn = txtUserSSN.getText().trim();
                if (idHasError == false && nameHasError == false) {
                    if (ssn.isEmpty()) {
                        ssnHasError = true;
                        JOptionPane.showMessageDialog(null, "SSN can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUserSSN.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, ssn, View.SSN_REGEX) != null) {
                            ssnHasError = false;
                        } else {
                            ssnHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input exactly 12 numbers!!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtUserSSN.requestFocus();
                        }
                    }
                }
            }
        });
        txtUserPhone.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String phone = txtUserPhone.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false) {
                    if (phone.isEmpty()) {
                        phoneHasError = true;
                        JOptionPane.showMessageDialog(null, "phone can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUserPhone.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, phone, View.PHONE_REGEX) != null) {
                            phoneHasError = false;
                        } else {
                            phoneHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input fomat 0 at the beginning and 9 remaining digits!!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtUserPhone.requestFocus();
                        }
                    }
                }
            }
        });
        txtUserEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String email = txtUserEmail.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false && phoneHasError == false) {
                    if (email.isEmpty()) {
                        emailHasError = true;
                        JOptionPane.showMessageDialog(null, "Email can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUserEmail.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, email, View.EMAIL_REGEX) != null) {
                            emailHasError = false;
                        } else {
                            emailHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input fomat example@gmail.com !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtUserEmail.requestFocus();
                        }
                    }
                }
            }
        });
        txtUserAddress.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String address = txtUserAddress.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false && phoneHasError == false && emailHasError == false) {
                    if (address.isEmpty()) {
                        countryHasError = true;
                        JOptionPane.showMessageDialog(null, "Country can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtUserAddress.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, address, View.TEXTNORMAL_REGEX) != null) {
                            countryHasError = false;
                        } else {
                            countryHasError = true;
                            JOptionPane.showMessageDialog(null, "Please invalid fomat !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtUserAddress.requestFocus();
                        }
                    }
                }
            }
        });

        if (idHasError == false && nameHasError == false
                && ssnHasError == false && phoneHasError == false && emailHasError == false && countryHasError == false) {
            return isValid = false;
        }
        return isValid;
    }

    public final boolean isCourse() {
        boolean isValid = true;
        txtCourseID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtCourseID.getText().trim();
                if (registerID != null && !registerID.isEmpty()) {
                    if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.COURSE_REGEX) != null) {
                        idHasError = false;
                    } else {
                        idHasError = true;
                        JOptionPane.showMessageDialog(null, "Please input format COR-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCourseID.requestFocus();
                    }
                } else {
                    idHasError = true;
                    JOptionPane.showMessageDialog(null, "CourseID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    txtCourseID.requestFocus();
                }
            }

        });
        txtCourseName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String name = txtCourseName.getText().trim();
                if (idHasError == false) {
                    if (name != null && !name.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, name, View.TEXT_REGEX) != null) {
                            nameHasError = false;
                        } else {
                            nameHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input capitalize the first letter !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtCourseName.requestFocus();
                        }
                    } else {
                        nameHasError = true;
                        JOptionPane.showMessageDialog(null, "Name can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCourseName.requestFocus();
                    }
                }
            }
        });
        txtCoachID1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtCoachID1.getText().trim();
                if (idHasError == false || nameHasError == false) {
                    if (registerID != null && !registerID.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.COACH_REGEX) != null) {
                            coachidHasError = false;
                        } else {
                            coachidHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input format COA-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtCoachID1.requestFocus();
                        }
                    } else {
                        coachidHasError = true;
                        JOptionPane.showMessageDialog(null, "CoachID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCoachID1.requestFocus();
                    }
                }
            }

        }
        );
        txtWorkOutID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtWorkOutID.getText().trim();
                if (idHasError == false || nameHasError == false || coachidHasError == false) {
                    if (registerID != null && !registerID.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.WORKOUT_REGEX) != null) {
                            workoutidHasError = false;
                        } else {
                            workoutidHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input format WKT-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtWorkOutID.requestFocus();
                        }
                    } else {
                        workoutidHasError = true;
                        JOptionPane.showMessageDialog(null, "WorkoutID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtWorkOutID.requestFocus();
                    }
                }
            }

        }
        );
        txtNutritionID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtNutritionID.getText().trim();
                if (idHasError == false || nameHasError == false || coachidHasError == false) {
                    if (registerID != null && !registerID.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.NUTITION_REGEX) != null) {
                            nutritionidHasError = false;
                        } else {
                            nutritionidHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input format NUT-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtNutritionID.requestFocus();
                        }
                    } else {
                        nutritionidHasError = true;
                        JOptionPane.showMessageDialog(null, "NutritionID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtNutritionID.requestFocus();
                    }
                }
            }

        }
        );
        txtDescription.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String name = txtDescription.getText().trim();
                if (idHasError == false || nameHasError == false || coachidHasError == false || workoutidHasError == false || nutritionidHasError == false) {
                    if (name != null && !name.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, name, View.TEXT_REGEX) != null) {
                            ssnHasError = false;
                        } else {
                            ssnHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input capitalize the first letter !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtCourseName.requestFocus();
                        }
                    } else {
                        ssnHasError = true;
                        JOptionPane.showMessageDialog(null, "Name can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCourseName.requestFocus();
                    }
                }
            }
        }
        );
        if (idHasError == false && nameHasError == false
                && coachidHasError == false && workoutidHasError == false && ssnHasError == false && nutritionidHasError == false) {
            return isValid = false;
        }
        return isValid;
    }

    public final boolean isValidation() {
        boolean isValid = true;
        txtCoachID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String registerID = txtCoachID.getText().trim();
                if (registerID != null && !registerID.isEmpty()) {
                    if (validation.checkStringInputFormJFRAME(rootPane, registerID, View.COACH_REGEX) != null) {
                        idHasError = false;
                    } else {
                        idHasError = true;
                        JOptionPane.showMessageDialog(null, "Please input format COA-YYYY!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtCoachID.requestFocus();
                    }
                } else {
                    idHasError = true;
                    JOptionPane.showMessageDialog(null, "ID can't be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    txtCoachID.requestFocus();
                }
            }

        });
        txtName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String name = txtName.getText().trim();
                if (idHasError == false) {
                    if (name != null && !name.isEmpty()) {
                        if (validation.checkStringInputFormJFRAME(rootPane, name, View.TEXT_REGEX) != null) {
                            nameHasError = false;
                        } else {
                            nameHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input capitalize the first letter !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtName.requestFocus();
                        }
                    } else {
                        nameHasError = true;
                        JOptionPane.showMessageDialog(null, "Name can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtName.requestFocus();
                    }
                }
            }
        });
        txtSSN.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String ssn = txtSSN.getText().trim();
                if (idHasError == false && nameHasError == false) {
                    if (ssn.isEmpty()) {
                        ssnHasError = true;
                        JOptionPane.showMessageDialog(null, "SSN can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtSSN.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, ssn, View.SSN_REGEX) != null) {
                            ssnHasError = false;
                        } else {
                            ssnHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input exactly 12 numbers!!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtSSN.requestFocus();
                        }
                    }
                }
            }
        });
        txtPhone.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String phone = txtPhone.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false) {
                    if (phone.isEmpty()) {
                        phoneHasError = true;
                        JOptionPane.showMessageDialog(null, "phone can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtPhone.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, phone, View.PHONE_REGEX) != null) {
                            phoneHasError = false;
                        } else {
                            phoneHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input fomat 0 at the beginning and 9 remaining digits!!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtPhone.requestFocus();
                        }
                    }
                }
            }
        });
        txtEmail.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String email = txtEmail.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false && phoneHasError == false) {
                    if (email.isEmpty()) {
                        emailHasError = true;
                        JOptionPane.showMessageDialog(null, "Email can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtEmail.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, email, View.EMAIL_REGEX) != null) {
                            emailHasError = false;
                        } else {
                            emailHasError = true;
                            JOptionPane.showMessageDialog(null, "Please input fomat example@gmail.com !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtEmail.requestFocus();
                        }
                    }
                }
            }
        });
        txtAddress.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String address = txtAddress.getText().trim();
                if (idHasError == false && nameHasError == false && ssnHasError == false && phoneHasError == false && emailHasError == false) {
                    if (address.isEmpty()) {
                        countryHasError = true;
                        JOptionPane.showMessageDialog(null, "Country can't empty!!", "Error", JOptionPane.ERROR_MESSAGE);
                        txtAddress.requestFocus();
                    } else {
                        if (validation.checkStringInputFormJFRAME(rootPane, address, View.TEXTNORMAL_REGEX) != null) {
                            countryHasError = false;
                        } else {
                            countryHasError = true;
                            JOptionPane.showMessageDialog(null, "Please invalid fomat !!", "Error", JOptionPane.ERROR_MESSAGE);
                            txtAddress.requestFocus();
                        }
                    }
                }
            }
        });

        if (idHasError == false && nameHasError == false
                && ssnHasError == false && phoneHasError == false && emailHasError == false && countryHasError == false) {
            return isValid = false;
        }
        return isValid;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        control = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        schedules = new javax.swing.JButton();
        coach = new javax.swing.JButton();
        User = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        Course = new javax.swing.JButton();
        labelName = new javax.swing.JLabel();
        background = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        labelMember = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelCoach = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        labelTotal = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel13 = new javax.swing.JLabel();
        CoachForm = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        txtCoachID = new javax.swing.JTextField();
        txtID = new javax.swing.JLabel();
        txtID1 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtID4 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        txtID2 = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        txtgender = new javax.swing.JLabel();
        getMale = new javax.swing.JRadioButton();
        getFemale = new javax.swing.JRadioButton();
        txtgender2 = new javax.swing.JLabel();
        getDay = new javax.swing.JComboBox<>();
        getMonth = new javax.swing.JComboBox<>();
        getYear = new javax.swing.JComboBox<>();
        txtID3 = new javax.swing.JLabel();
        txtAddress = new javax.swing.JTextField();
        txtID6 = new javax.swing.JLabel();
        txtSalary = new javax.swing.JTextField();
        levelbox = new javax.swing.JComboBox<>();
        txtID7 = new javax.swing.JLabel();
        add = new javax.swing.JButton();
        update = new javax.swing.JButton();
        delete = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        txtSSN = new javax.swing.JTextField();
        txtID22 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableCoach = new javax.swing.JTable();
        UserForm = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        txtUserID = new javax.swing.JTextField();
        txtID5 = new javax.swing.JLabel();
        txtID8 = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        txtID9 = new javax.swing.JLabel();
        txtUserPhone = new javax.swing.JTextField();
        txtID10 = new javax.swing.JLabel();
        txtUserEmail = new javax.swing.JTextField();
        txtgender1 = new javax.swing.JLabel();
        getMale1 = new javax.swing.JRadioButton();
        getFemale1 = new javax.swing.JRadioButton();
        txtgender3 = new javax.swing.JLabel();
        getDay1 = new javax.swing.JComboBox<>();
        getMonth1 = new javax.swing.JComboBox<>();
        getYear1 = new javax.swing.JComboBox<>();
        txtID11 = new javax.swing.JLabel();
        txtUserAddress = new javax.swing.JTextField();
        txtID12 = new javax.swing.JLabel();
        txtheight = new javax.swing.JTextField();
        txtID13 = new javax.swing.JLabel();
        UserUpdate = new javax.swing.JButton();
        DeleteUser = new javax.swing.JButton();
        resetUser = new javax.swing.JButton();
        txtWeight = new javax.swing.JTextField();
        SSN = new javax.swing.JLabel();
        txtUserSSN = new javax.swing.JTextField();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableUser = new javax.swing.JTable();
        CourseForm = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        txtID14 = new javax.swing.JLabel();
        txtCourseID = new javax.swing.JTextField();
        txtCourseName = new javax.swing.JTextField();
        txtCoachID1 = new javax.swing.JTextField();
        txtWorkOutID = new javax.swing.JTextField();
        txtNutritionID = new javax.swing.JTextField();
        txtID15 = new javax.swing.JLabel();
        txtID16 = new javax.swing.JLabel();
        txtID17 = new javax.swing.JLabel();
        txtID18 = new javax.swing.JLabel();
        txtID19 = new javax.swing.JLabel();
        txtID20 = new javax.swing.JLabel();
        txtID21 = new javax.swing.JLabel();
        txtTotalWeek = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtDescription = new javax.swing.JTextField();
        add1 = new javax.swing.JButton();
        update1 = new javax.swing.JButton();
        delete1 = new javax.swing.JButton();
        reset1 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tableCourse = new javax.swing.JTable();
        ScheduleForm = new javax.swing.JPanel();
        jSeparator7 = new javax.swing.JSeparator();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel14 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        txtsSearchID = new javax.swing.JTextField();
        searchSchedule = new javax.swing.JButton();
        updateSchedule = new javax.swing.JButton();
        comboBoxSchedule = new javax.swing.JComboBox<>();
        txtUpdate = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        Progress = new javax.swing.JButton();
        labelInput = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSchedule = new javax.swing.JTable();
        jPanel16 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableWorkout = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        AddWorkout = new javax.swing.JButton();
        txtsWorkoutID = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txtWorkoutName = new javax.swing.JTextField();
        txtsExercises = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        resetWorkout = new javax.swing.JButton();
        deleteWorkout = new javax.swing.JButton();
        jSeparator8 = new javax.swing.JSeparator();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tableNutrition = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtNutritionID1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtNutrition = new javax.swing.JTextField();
        AddNutrition = new javax.swing.JButton();
        deleteNutrition = new javax.swing.JButton();
        resetNutrition = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        control.setBackground(new java.awt.Color(68, 222, 222));
        control.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/schedule_8161423.png"))); // NOI18N
        jLabel1.setText("Fitness Management System");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_1077114.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setText("Welcome");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        schedules.setBackground(new java.awt.Color(204, 255, 255));
        schedules.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        schedules.setForeground(new java.awt.Color(0, 153, 153));
        schedules.setText("Manage");
        schedules.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        schedules.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schedulesActionPerformed(evt);
            }
        });

        coach.setBackground(new java.awt.Color(204, 255, 255));
        coach.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        coach.setForeground(new java.awt.Color(0, 153, 153));
        coach.setText("Coaches");
        coach.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        coach.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coachActionPerformed(evt);
            }
        });

        User.setBackground(new java.awt.Color(204, 255, 255));
        User.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        User.setForeground(new java.awt.Color(0, 153, 153));
        User.setText("Members");
        User.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        User.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserActionPerformed(evt);
            }
        });

        logout.setBackground(new java.awt.Color(255, 153, 153));
        logout.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logout_12080248.png"))); // NOI18N
        logout.setText("Log out");
        logout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        logout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logoutActionPerformed(evt);
            }
        });

        Course.setBackground(new java.awt.Color(204, 255, 255));
        Course.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Course.setForeground(new java.awt.Color(0, 153, 153));
        Course.setText("Course");
        Course.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Course.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CourseActionPerformed(evt);
            }
        });

        labelName.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelName.setForeground(new java.awt.Color(242, 242, 242));
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelName.setText("Welcome");

        javax.swing.GroupLayout controlLayout = new javax.swing.GroupLayout(control);
        control.setLayout(controlLayout);
        controlLayout.setHorizontalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(118, 118, 118)
                                .addComponent(jLabel2))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(coach, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(schedules, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Course, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(94, 94, 94)
                                .addComponent(jLabel3))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlLayout.setVerticalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(labelName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
                .addComponent(schedules, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(Course, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(coach, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(User, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(logout)
                .addGap(47, 47, 47))
        );

        background.setLayout(new java.awt.CardLayout());

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel5.setBackground(new java.awt.Color(68, 222, 222));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_11188936.png"))); // NOI18N

        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(242, 242, 242));
        jLabel5.setText("Number of member");

        labelMember.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelMember.setForeground(new java.awt.Color(242, 242, 242));
        labelMember.setText("0");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelMember)
                .addGap(28, 28, 28))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(90, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(14, 14, 14))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(labelMember)
                        .addGap(70, 70, 70))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(68, 222, 222));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_11188936.png"))); // NOI18N

        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(242, 242, 242));
        jLabel8.setText("Number of coach");

        labelCoach.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelCoach.setForeground(new java.awt.Color(242, 242, 242));
        labelCoach.setText("0");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(117, Short.MAX_VALUE)
                .addComponent(jLabel8)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(labelCoach)
                .addGap(25, 25, 25))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(labelCoach)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)))
                .addComponent(jLabel8)
                .addContainerGap())
        );

        jPanel7.setBackground(new java.awt.Color(68, 222, 222));

        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pound-sterling_15927782.png"))); // NOI18N

        labelTotal.setFont(new java.awt.Font("SansSerif", 0, 18)); // NOI18N
        labelTotal.setForeground(new java.awt.Color(242, 242, 242));
        labelTotal.setText("$0.0");

        jLabel12.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(242, 242, 242));
        jLabel12.setText("Total's Income");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 96, Short.MAX_VALUE)
                .addComponent(labelTotal)
                .addGap(24, 24, 24))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(14, 14, 14))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jLabel10))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(labelTotal)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(118, 118, 118)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/freepik__upload__78048 (1).png"))); // NOI18N
        jLabel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE)
            .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator2, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainLayout.createSequentialGroup()
                    .addGap(199, 199, 199)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(514, Short.MAX_VALUE)))
        );

        background.add(main, "card2");

        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID.setText("Coach ID");
        txtID.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID1.setText("Name");
        txtID1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID4.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID4.setText("Phone");
        txtID4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID2.setText("Email");
        txtID2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        txtgender.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtgender.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtgender.setText("Gender");
        txtgender.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        getMale.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getMale.setText("Male");
        getMale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMaleActionPerformed(evt);
            }
        });

        getFemale.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getFemale.setText("Female");
        getFemale.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFemaleActionPerformed(evt);
            }
        });

        txtgender2.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtgender2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtgender2.setText("Day of birth");
        txtgender2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        getDay.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        getDay.setSelectedIndex(15);

        getMonth.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        getMonth.setSelectedIndex(5);

        getYear.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006" }));
        getYear.setSelectedIndex(10);
        getYear.setToolTipText("");

        txtID3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID3.setText("Address");
        txtID3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID6.setText("Salary");
        txtID6.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        levelbox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Entry-Level Trainer", "Intermediate Trainer", "Advanced Trainer" }));

        txtID7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID7.setText("Level");
        txtID7.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        add.setBackground(new java.awt.Color(204, 255, 255));
        add.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add.setForeground(new java.awt.Color(0, 153, 153));
        add.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circle_16597173.png"))); // NOI18N
        add.setText("Add");
        add.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addActionPerformed(evt);
            }
        });

        update.setBackground(new java.awt.Color(204, 255, 255));
        update.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        update.setForeground(new java.awt.Color(0, 153, 153));
        update.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/software_3067531.png"))); // NOI18N
        update.setText("Update");
        update.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        delete.setBackground(new java.awt.Color(204, 255, 255));
        delete.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        delete.setForeground(new java.awt.Color(0, 153, 153));
        delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_10024226.png"))); // NOI18N
        delete.setText("Delete");
        delete.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteActionPerformed(evt);
            }
        });

        reset.setBackground(new java.awt.Color(204, 255, 255));
        reset.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        reset.setForeground(new java.awt.Color(0, 153, 153));
        reset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-arrow_11507304.png"))); // NOI18N
        reset.setText("Reset");
        reset.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        jSeparator3.setForeground(new java.awt.Color(0, 0, 0));

        txtID22.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID22.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID22.setText("SSN");
        txtID22.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtCoachID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(txtgender, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(28, 28, 28)
                                .addComponent(txtgender2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtID4, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtID22, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtID2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtID3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addComponent(getMale, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(getFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGap(2, 2, 2)
                                        .addComponent(getDay, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(getYear, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(30, 30, 30))
                            .addComponent(txtAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtID7, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtID6, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(levelbox, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 12, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                                .addGap(108, 108, 108)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(add, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(update, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(delete, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator3)
                    .addContainerGap()))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCoachID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgender, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getMale, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID6, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSalary, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(levelbox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(getDay, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(getYear, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtgender2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID2, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(add, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(delete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(reset, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID3, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtID22, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(317, 317, 317))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(508, 508, 508)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));

        jPanel8.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableCoach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "Name", "Phone", "Email", "SSN", "DOB", "Gender", "Address", "Salary", "Level"
            }
        ));
        tableCoach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCoachMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tableCoach);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout CoachFormLayout = new javax.swing.GroupLayout(CoachForm);
        CoachForm.setLayout(CoachFormLayout);
        CoachFormLayout.setHorizontalGroup(
            CoachFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoachFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CoachFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(CoachFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CoachFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        CoachFormLayout.setVerticalGroup(
            CoachFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CoachFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(CoachFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CoachFormLayout.createSequentialGroup()
                    .addGap(219, 219, 219)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(494, Short.MAX_VALUE)))
        );

        background.add(CoachForm, "card3");

        jPanel11.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtID5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID5.setText("User ID");
        txtID5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID8.setText("Name");
        txtID8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID9.setText("Phone");
        txtID9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID10.setText("Email");
        txtID10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtgender1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtgender1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtgender1.setText("Gender");
        txtgender1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        getMale1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getMale1.setText("Male");
        getMale1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getMale1ActionPerformed(evt);
            }
        });

        getFemale1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getFemale1.setText("Female");
        getFemale1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFemale1ActionPerformed(evt);
            }
        });

        txtgender3.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtgender3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtgender3.setText("Day of birth");
        txtgender3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        getDay1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getDay1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        getDay1.setSelectedIndex(15);

        getMonth1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getMonth1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        getMonth1.setSelectedIndex(5);

        getYear1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        getYear1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006" }));
        getYear1.setSelectedIndex(10);
        getYear1.setToolTipText("");

        txtID11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID11.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID11.setText("Address");
        txtID11.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID12.setText("Height");
        txtID12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID13.setText("Weight");
        txtID13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        UserUpdate.setBackground(new java.awt.Color(204, 255, 255));
        UserUpdate.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        UserUpdate.setForeground(new java.awt.Color(0, 153, 153));
        UserUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/software_3067531.png"))); // NOI18N
        UserUpdate.setText("Update");
        UserUpdate.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        UserUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserUpdateActionPerformed(evt);
            }
        });

        DeleteUser.setBackground(new java.awt.Color(204, 255, 255));
        DeleteUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        DeleteUser.setForeground(new java.awt.Color(0, 153, 153));
        DeleteUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_10024226.png"))); // NOI18N
        DeleteUser.setText("Delete");
        DeleteUser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        DeleteUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteUserActionPerformed(evt);
            }
        });

        resetUser.setBackground(new java.awt.Color(204, 255, 255));
        resetUser.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        resetUser.setForeground(new java.awt.Color(0, 153, 153));
        resetUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-arrow_11507304.png"))); // NOI18N
        resetUser.setText("Reset");
        resetUser.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resetUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetUserActionPerformed(evt);
            }
        });

        SSN.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SSN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SSN.setText("SSN");
        SSN.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addComponent(txtID5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91)
                                .addComponent(txtgender1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(222, 222, 222)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel11Layout.createSequentialGroup()
                                        .addComponent(txtID11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtgender3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtID10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtID9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtID8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUserPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(getMale1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(getFemale1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(getDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(getMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(getYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtUserAddress))
                .addGap(36, 36, 36)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtID13, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                            .addComponent(SSN, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtID12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(UserUpdate)
                        .addGap(17, 17, 17)))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtheight, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(DeleteUser, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                        .addGap(29, 29, 29)
                        .addComponent(resetUser, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgender1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getMale1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getFemale1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtheight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtgender3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(39, 39, 39)
                        .addComponent(txtID11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(39, 39, 39))
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(txtID13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(getMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(getYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(getDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtWeight, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(txtID8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUserSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SSN, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(UserUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(DeleteUser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(resetUser, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel11Layout.createSequentialGroup()
                                .addComponent(txtUserPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtID10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(txtUserAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))))
        );

        jSeparator5.setForeground(new java.awt.Color(0, 0, 0));

        jPanel12.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "UserID", "Name", "Phone", "Email", "SSN", "DOB", "Gender", "Address", "Height", "Weight"
            }
        ));
        tableUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableUserMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tableUser);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 991, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout UserFormLayout = new javax.swing.GroupLayout(UserForm);
        UserForm.setLayout(UserFormLayout);
        UserFormLayout.setHorizontalGroup(
            UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserFormLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UserFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator5)
                    .addContainerGap()))
            .addGroup(UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UserFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        UserFormLayout.setVerticalGroup(
            UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserFormLayout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 517, Short.MAX_VALUE))
            .addGroup(UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(UserFormLayout.createSequentialGroup()
                    .addGap(202, 202, 202)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(511, Short.MAX_VALUE)))
            .addGroup(UserFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, UserFormLayout.createSequentialGroup()
                    .addGap(0, 285, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        background.add(UserForm, "card4");

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtID14.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID14.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID14.setText("Course ID");
        txtID14.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID15.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID15.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID15.setText("Coach ID");
        txtID15.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID16.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID16.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID16.setText("Course Name");
        txtID16.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID17.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID17.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID17.setText("nutritionID");
        txtID17.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID18.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID18.setText(" Price");
        txtID18.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID19.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID19.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID19.setText("Workout ID");
        txtID19.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID20.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID20.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID20.setText("Total Week");
        txtID20.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID21.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID21.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID21.setText("Description");
        txtID21.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        add1.setBackground(new java.awt.Color(204, 255, 255));
        add1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        add1.setForeground(new java.awt.Color(0, 153, 153));
        add1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circle_16597173.png"))); // NOI18N
        add1.setText("Add");
        add1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        add1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add1ActionPerformed(evt);
            }
        });

        update1.setBackground(new java.awt.Color(204, 255, 255));
        update1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        update1.setForeground(new java.awt.Color(0, 153, 153));
        update1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/software_3067531.png"))); // NOI18N
        update1.setText("Update");
        update1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        update1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update1ActionPerformed(evt);
            }
        });

        delete1.setBackground(new java.awt.Color(204, 255, 255));
        delete1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        delete1.setForeground(new java.awt.Color(0, 153, 153));
        delete1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_10024226.png"))); // NOI18N
        delete1.setText("Delete");
        delete1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        delete1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete1ActionPerformed(evt);
            }
        });

        reset1.setBackground(new java.awt.Color(204, 255, 255));
        reset1.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        reset1.setForeground(new java.awt.Color(0, 153, 153));
        reset1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-arrow_11507304.png"))); // NOI18N
        reset1.setText("Reset");
        reset1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        reset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtID18, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID14, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID15, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID17, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID19, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID16)
                            .addComponent(txtID20, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID21, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTotalWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtCoachID1, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtWorkOutID, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNutritionID, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(add1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(delete1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reset1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(update1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(62, 62, 62))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID14, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourseName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID16, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID15, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCoachID1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID19, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtWorkOutID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNutritionID, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID17, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID18, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID20, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalWeek, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID21, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(add1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(update1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(reset1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(delete1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(96, Short.MAX_VALUE))
        );

        jSeparator6.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator6.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jPanel9.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, "", null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "CourseID", "CourseName", "CoachID", "WorkoutID", "NutritionID", "Price", "Total Week", "Description"
            }
        ));
        tableCourse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableCourseMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tableCourse);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        javax.swing.GroupLayout CourseFormLayout = new javax.swing.GroupLayout(CourseForm);
        CourseForm.setLayout(CourseFormLayout);
        CourseFormLayout.setHorizontalGroup(
            CourseFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CourseFormLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(CourseFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CourseFormLayout.createSequentialGroup()
                    .addGap(312, 312, 312)
                    .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(692, Short.MAX_VALUE)))
        );
        CourseFormLayout.setVerticalGroup(
            CourseFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CourseFormLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(CourseFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(CourseFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(CourseFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator6)
                    .addContainerGap()))
        );

        background.add(CourseForm, "card6");

        jSeparator7.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator7.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jTabbedPane1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(300, 300));

        jPanel18.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtsSearchID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsSearchID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsSearchID.setToolTipText("");

        searchSchedule.setBackground(new java.awt.Color(204, 255, 255));
        searchSchedule.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        searchSchedule.setForeground(new java.awt.Color(0, 153, 153));
        searchSchedule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/search_10023926.png"))); // NOI18N
        searchSchedule.setText("Search");
        searchSchedule.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchScheduleActionPerformed(evt);
            }
        });

        updateSchedule.setBackground(new java.awt.Color(204, 255, 255));
        updateSchedule.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        updateSchedule.setForeground(new java.awt.Color(0, 153, 153));
        updateSchedule.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/software_3067531.png"))); // NOI18N
        updateSchedule.setText("Update");
        updateSchedule.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        updateSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateScheduleActionPerformed(evt);
            }
        });

        comboBoxSchedule.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TypeUser", "SessionDate", "Time", "Exercises" }));
        comboBoxSchedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxScheduleActionPerformed(evt);
            }
        });

        txtUpdate.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtUpdate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUpdate.setToolTipText("");

        jLabel14.setText("Schedule ID ");

        Progress.setBackground(new java.awt.Color(204, 255, 255));
        Progress.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Progress.setForeground(new java.awt.Color(0, 153, 153));
        Progress.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/fire.png"))); // NOI18N
        Progress.setText("Progress");
        Progress.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Progress.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProgressActionPerformed(evt);
            }
        });

        labelInput.setText("New TypeUser");

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(labelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel18Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtsSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(updateSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 141, Short.MAX_VALUE)
                .addComponent(Progress, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(99, 99, 99))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsSearchID, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(updateSchedule, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxSchedule, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
                    .addComponent(labelInput, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(12, 12, 12))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel18Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Progress, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30))
        );

        tableSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Schedule ID", "User ID", "Course ID", "WorkoutID", "Type User", "Session Date", "Start Time", "End Time", "Exercies"
            }
        ));
        jScrollPane5.setViewportView(tableSchedule);
        if (tableSchedule.getColumnModel().getColumnCount() > 0) {
            tableSchedule.getColumnModel().getColumn(8).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 513, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane1.addTab("Schedule", new javax.swing.ImageIcon(getClass().getResource("/img/calendar_2693507.png")), jPanel14); // NOI18N

        tableWorkout.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableWorkout.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Workout ID", "WorkoutName", "Exercises"
            }
        ));
        jScrollPane1.setViewportView(tableWorkout);
        if (tableWorkout.getColumnModel().getColumnCount() > 0) {
            tableWorkout.getColumnModel().getColumn(0).setPreferredWidth(10);
        }

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        AddWorkout.setBackground(new java.awt.Color(204, 255, 255));
        AddWorkout.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        AddWorkout.setForeground(new java.awt.Color(0, 153, 153));
        AddWorkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circle_16597173.png"))); // NOI18N
        AddWorkout.setText("Add");
        AddWorkout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AddWorkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddWorkoutActionPerformed(evt);
            }
        });

        txtsWorkoutID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsWorkoutID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsWorkoutID.setToolTipText("");

        jLabel15.setText("Workout ID ");

        txtWorkoutName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtWorkoutName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtWorkoutName.setToolTipText("");

        txtsExercises.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtsExercises.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtsExercises.setToolTipText("");

        jLabel16.setText("Workout Name");

        jLabel17.setText("Exercises");

        resetWorkout.setBackground(new java.awt.Color(204, 255, 255));
        resetWorkout.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        resetWorkout.setForeground(new java.awt.Color(0, 153, 153));
        resetWorkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-arrow_11507304.png"))); // NOI18N
        resetWorkout.setText("Reset");
        resetWorkout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resetWorkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetWorkoutActionPerformed(evt);
            }
        });

        deleteWorkout.setBackground(new java.awt.Color(204, 255, 255));
        deleteWorkout.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        deleteWorkout.setForeground(new java.awt.Color(0, 153, 153));
        deleteWorkout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_10024226.png"))); // NOI18N
        deleteWorkout.setText("Delete");
        deleteWorkout.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteWorkout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteWorkoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtsWorkoutID, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtsExercises, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtWorkoutName, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(AddWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addComponent(deleteWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(resetWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsWorkoutID, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtsExercises, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWorkoutName, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteWorkout, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24))
        );

        jSeparator8.setForeground(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jSeparator8, javax.swing.GroupLayout.DEFAULT_SIZE, 1048, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 494, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel16Layout.createSequentialGroup()
                    .addGap(163, 163, 163)
                    .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(507, Short.MAX_VALUE)))
        );

        jTabbedPane1.addTab("Workout", new javax.swing.ImageIcon(getClass().getResource("/img/gym_6966411.png")), jPanel16); // NOI18N

        jPanel17.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableNutrition.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableNutrition.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nutrition ID", "Nutrition"
            }
        ));
        jScrollPane6.setViewportView(tableNutrition);
        if (tableNutrition.getColumnModel().getColumnCount() > 0) {
            tableNutrition.getColumnModel().getColumn(1).setPreferredWidth(500);
        }

        jPanel13.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel18.setText("Nutrition ID");

        txtNutritionID1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNutritionID1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNutritionID1.setToolTipText("");

        jLabel19.setText("Nutrition");

        txtNutrition.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtNutrition.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNutrition.setToolTipText("");

        AddNutrition.setBackground(new java.awt.Color(204, 255, 255));
        AddNutrition.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        AddNutrition.setForeground(new java.awt.Color(0, 153, 153));
        AddNutrition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/circle_16597173.png"))); // NOI18N
        AddNutrition.setText("Add");
        AddNutrition.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AddNutrition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNutritionActionPerformed(evt);
            }
        });

        deleteNutrition.setBackground(new java.awt.Color(204, 255, 255));
        deleteNutrition.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        deleteNutrition.setForeground(new java.awt.Color(0, 153, 153));
        deleteNutrition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/delete_10024226.png"))); // NOI18N
        deleteNutrition.setText("Delete");
        deleteNutrition.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        deleteNutrition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteNutritionActionPerformed(evt);
            }
        });

        resetNutrition.setBackground(new java.awt.Color(204, 255, 255));
        resetNutrition.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        resetNutrition.setForeground(new java.awt.Color(0, 153, 153));
        resetNutrition.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/refresh-arrow_11507304.png"))); // NOI18N
        resetNutrition.setText("Reset");
        resetNutrition.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        resetNutrition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetNutritionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNutritionID1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(61, 61, 61)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(AddNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(resetNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(deleteNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNutritionID1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resetNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(deleteNutrition, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16))))
        );

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
            .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Nutrition", new javax.swing.ImageIcon(getClass().getResource("/img/nutrition_7757741.png")), jPanel17); // NOI18N

        javax.swing.GroupLayout ScheduleFormLayout = new javax.swing.GroupLayout(ScheduleForm);
        ScheduleForm.setLayout(ScheduleFormLayout);
        ScheduleFormLayout.setHorizontalGroup(
            ScheduleFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ScheduleFormLayout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 993, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(ScheduleFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ScheduleFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(998, Short.MAX_VALUE)))
        );
        ScheduleFormLayout.setVerticalGroup(
            ScheduleFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScheduleFormLayout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
            .addGroup(ScheduleFormLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(ScheduleFormLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 688, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        background.add(ScheduleForm, "card5");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1007, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 716, Short.MAX_VALUE)
        );

        background.add(jPanel10, "card7");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(background, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        this.setVisible(false);
        new Login().setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }//GEN-LAST:event_logoutActionPerformed

    private void coachActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coachActionPerformed
        main.setVisible(false);
        CourseForm.setVisible(false);
        UserForm.setVisible(false);
        CoachForm.setVisible(true);
        viewCoachTable();

    }//GEN-LAST:event_coachActionPerformed

    private void CourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CourseActionPerformed
        main.setVisible(false);
        CourseForm.setVisible(true);
        UserForm.setVisible(false);
        CoachForm.setVisible(false);
        viewCourseTable();
    }//GEN-LAST:event_CourseActionPerformed

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        txtCoachID.setText("");
        txtName.setText("");
        txtAddress.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtSalary.setText("");
        txtSSN.setText("");
    }//GEN-LAST:event_resetActionPerformed

    private void UserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserActionPerformed
        main.setVisible(false);
        CourseForm.setVisible(false);
        UserForm.setVisible(true);
        CoachForm.setVisible(false);
        viewUserTable();
    }//GEN-LAST:event_UserActionPerformed

    private void addActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addActionPerformed
        if (isValidation() == false) {
            String coachID;
            coachID = txtCoachID.getText().trim();
            if (new CoachService().findByID(coachID) == null) {
                String name = txtName.getText().trim();
                String ssn = txtSSN.getText().trim();
                String phone = txtPhone.getText().trim();
                String email = txtEmail.getText().trim();
                String address = txtAddress.getText().trim();
                String[] part = address.split(",");
                if (part.length == 3) {
                    String street = part[0].trim();
                    String city = part[1].trim();
                    String country = part[2].trim();
                    Address x = new Address(country, city, street);
                    String day = getDay.getSelectedItem().toString();
                    String month = getMonth.getSelectedItem().toString();
                    String year = getYear.getSelectedItem().toString();
                    String birthdayString = day + "/" + month + "/" + year;
                    LocalDate birthDay = validation.convertStringToLocalDate(birthdayString);
                    boolean gender = getMale.isSelected();
                    double salary = validation.checkDoubleInputFromJFRAM(rootPane, txtSalary.getText().trim(), "Coach's Salary");
                    String level = levelbox.getSelectedItem().toString();
                    if (salary > 0) {
                        new CoachService().add(new Coach(coachID, name, ssn, phone, gender, birthDay, email, x, level, salary));
                        viewCoachTable();
                        JOptionPane.showMessageDialog(this, "Add coach sucessfully !!");
                    } else {
                        JOptionPane.showMessageDialog(rootPane, txtSalary.getText().trim() + " can't be negative.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Address invalid fomat(name,name,name)", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Coach ID exsit !!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_addActionPerformed

    private void tableCoachMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCoachMouseClicked
        pos = this.tableCoach.getSelectedRow();
        viewCoach();
    }//GEN-LAST:event_tableCoachMouseClicked

    private void deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteActionPerformed
        String coachID = txtCoachID.getText().toUpperCase();
        if (coachService.getAllCoach().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty list . No search can be perform !!", "Erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            Coach coach = new CoachService().findByID(coachID);
            if (coach == null) {
                JOptionPane.showMessageDialog(this, "Coach doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                coachService.delete(coachID);
                viewCoachTable();
                JOptionPane.showMessageDialog(this, "Delete sucessfully !!");
            }
        }
    }//GEN-LAST:event_deleteActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        String coachID = txtCoachID.getText().toUpperCase();
        if (isValidation() == false) {
            if (coachService.getAllCoach().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty list. No search can be performed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Coach coach = coachService.findByID(coachID);
            if (coach == null) {
                JOptionPane.showMessageDialog(this, "Coach doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = txtName.getText().trim();
                String ssn = txtSSN.getText().trim();
                String phone = txtPhone.getText().trim();
                String email = txtEmail.getText().trim();
                String address = txtAddress.getText().trim();

                String[] part = address.split(",");
                if (part.length != 3) {
                    JOptionPane.showMessageDialog(this, "Address invalid format. Please use 'Street, City, Country'.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String street = part[0].trim();
                String city = part[1].trim();
                String country = part[2].trim();
                Address updatedAddress = new Address(country, city, street);
                String day = getDay.getSelectedItem().toString();
                String month = getMonth.getSelectedItem().toString();
                String year = getYear.getSelectedItem().toString();
                LocalDate birthDay = validation.convertStringToLocalDate(day + "/" + month + "/" + year);

                boolean gender = getMale.isSelected();
                double salary = validation.checkDoubleInputFromJFRAM(rootPane, txtSalary.getText().trim(), "Coach's Salary");

                if (salary <= 0) {
                    JOptionPane.showMessageDialog(this, "Salary can't be negative or zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String level = levelbox.getSelectedItem().toString();
                coach.setName(name);
                coach.setCmnd(ssn);
                coach.setPhoneNumber(phone);
                coach.setEmail(email);
                coach.setAddress(updatedAddress);
                coach.setBirthday(birthDay);
                coach.setSex(gender);
                coach.setSalary(salary);
                coach.setSpecialization(level);
                coachService.update(coach);
                viewCoachTable();
                JOptionPane.showMessageDialog(this, "Update successfully!");
            }
        }
    }//GEN-LAST:event_updateActionPerformed

    private void UserUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserUpdateActionPerformed
        if (isCustomer() == false) {
            String userID = txtUserID.getText().toUpperCase();
            if (userService.getAllUsers().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty list. No search can be performed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Users user = new UserService().findByID(userID);
            if (user == null) {
                JOptionPane.showMessageDialog(this, "User doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                String name = txtUserName.getText().trim();
                String ssn = txtUserSSN.getText().trim();
                String phone = txtUserPhone.getText().trim();
                String email = txtUserEmail.getText().trim();
                String address = txtUserAddress.getText().trim();

                String[] part = address.split(",");
                if (part.length != 3) {
                    JOptionPane.showMessageDialog(this, "Address invalid format. Please use 'Street, City, Country'.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String street = part[0].trim();
                String city = part[1].trim();
                String country = part[2].trim();
                Address updatedAddress = new Address(country, city, street);
                String day = getDay1.getSelectedItem().toString();
                String month = getMonth1.getSelectedItem().toString();
                String year = getYear1.getSelectedItem().toString();
                LocalDate birthDay = validation.convertStringToLocalDate(day + "/" + month + "/" + year);

                boolean gender = getMale1.isSelected();
                double height = validation.checkDoubleInputFromJFRAM(rootPane, txtheight.getText().trim(), "User's height");
                double weight = validation.checkDoubleInputFromJFRAM(rootPane, txtWeight.getText().trim(), "User's weight");
                if (height <= 0 || weight <= 0) {
                    JOptionPane.showMessageDialog(this, "Height or Weight can't be negative or zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                user.setName(name);
                user.setCmnd(ssn);
                user.setAddress(updatedAddress);
                user.setEmail(email);
                user.setBirthday(birthDay);
                user.setPhoneNumber(phone);
                user.setSex(gender);
                user.setHeight(height);
                user.setWeight(weight);
                userService.update(user);
                viewUserTable();
                JOptionPane.showMessageDialog(this, "Update successfully!");
            }
        }
    }//GEN-LAST:event_UserUpdateActionPerformed

    private void resetUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetUserActionPerformed
        txtUserID.setText("");
        txtUserName.setText("");
        txtUserAddress.setText("");
        txtUserEmail.setText("");
        txtUserPhone.setText("");
        txtheight.setText("");
        txtWeight.setText("");
        txtUserSSN.setText("");
    }//GEN-LAST:event_resetUserActionPerformed

    private void tableUserMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableUserMouseClicked
        pos = this.tableUser.getSelectedRow();
        viewUser();
    }//GEN-LAST:event_tableUserMouseClicked

    private void add1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add1ActionPerformed
        if (isCourse() == false) {
            String courseID;
            courseID = txtCourseID.getText().trim();
            if (courseService.findByID(courseID) == null) {
                String coachID;
                coachID = txtCoachID1.getText().trim();
                if (new CoachService().findByID(coachID) != null) {
                    String name = txtCourseName.getText().trim();
                    String workoutID = txtWorkOutID.getText().trim();
                    if (new WorkoutService().findByID(workoutID) != null) {
                        String nutritionID = txtNutritionID.getText().trim();
                        if (new NutritionService().findByID(nutritionID) != null) {
                            String description = txtDescription.getText().trim();
                            int totalWeek = validation.checkIntInputFromJFRAM(rootPane, txtTotalWeek.getText().trim(), "TotalWeek");
                            double price = validation.checkDoubleInputFromJFRAM(rootPane, txtPrice.getText().trim(), "Price");
                            if (totalWeek > 0 && price > 0) {
                                courseService.createCourse(new Course(courseID, name, coachID, workoutID, nutritionID, price, totalWeek, description));
                                JOptionPane.showMessageDialog(this, "Add successfully!");
                                viewCourseTable();
                            } else {
                                JOptionPane.showMessageDialog(this, "Price or totalWeek can't be negative or zero.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(rootPane, "Nutrition ID not found !!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(rootPane, "Workout ID not found !!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Coach ID not found !!", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(rootPane, "Course ID exsit !!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }


    }//GEN-LAST:event_add1ActionPerformed

    private void reset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset1ActionPerformed
        txtCourseID.setText("");
        txtCourseName.setText("");
        txtCoachID1.setText("");
        txtWorkOutID.setText("");
        txtDescription.setText("");
        txtPrice.setText("");
        txtTotalWeek.setText("");
        txtNutritionID.setText("");
    }//GEN-LAST:event_reset1ActionPerformed

    private void update1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update1ActionPerformed
        if (isCourse() == false) {
            String courseID = txtCourseID.getText().trim();
            if (courseService.getListCourse().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Empty list. No update can be performed!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Course course = courseService.findByID(courseID);
            if (course == null) {
                JOptionPane.showMessageDialog(this, "Course doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String coachID = txtCoachID1.getText().trim();
            if (new CoachService().findByID(coachID) == null) {
                JOptionPane.showMessageDialog(this, "Coach ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String workoutID = txtWorkOutID.getText().trim();
            if (new WorkoutService().findByID(workoutID) == null) {
                JOptionPane.showMessageDialog(this, "Workout ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nutritionID = txtNutritionID.getText().trim();
            if (new NutritionService().findByID(nutritionID) == null) {
                JOptionPane.showMessageDialog(this, "Nutrition ID not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String name = txtCourseName.getText().trim();
            String description = txtDescription.getText().trim();
            int totalWeek = (int) validation.checkIntInputFromJFRAM(rootPane, txtTotalWeek.getText().trim(), "Total Week");
            double price = validation.checkDoubleInputFromJFRAM(rootPane, txtheight.getText().trim(), "Price");
            if (totalWeek <= 0 || price <= 0) {
                JOptionPane.showMessageDialog(this, "Price or total week can't be negative or zero.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            course.setCourseName(name);
            course.setCoachID(coachID);
            course.setWorkoutID(workoutID);
            course.setNutritionID(nutritionID);
            course.setTotalWeek(totalWeek);
            course.setPrice(price);
            course.setDescription(description);
            courseService.update(course);
            viewCourseTable();
            JOptionPane.showMessageDialog(this, "Update successful!");
        }
    }//GEN-LAST:event_update1ActionPerformed

    private void delete1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete1ActionPerformed
        String courseID = txtCourseID.getText().toUpperCase();
        if (courseService.getListCourse().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty list . No search can be perform !!", "Erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            Course couser = courseService.findByID(courseID);
            if (couser == null) {
                JOptionPane.showMessageDialog(this, "Course doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                courseService.delete(courseID);
                viewCourseTable();
                JOptionPane.showMessageDialog(this, "Delete sucessfully !!");
            }
        }
    }//GEN-LAST:event_delete1ActionPerformed

    private void DeleteUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteUserActionPerformed
        String userID = txtUserID.getText().toUpperCase();
        if (userService.getAllUsers().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty list . No search can be perform !!", "Erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            Users couser = userService.findByID(userID);
            if (couser == null) {
                JOptionPane.showMessageDialog(this, "UserID doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                new LoginService().delete(userID);
                userService.delete(userID);
                viewUserTable();
                JOptionPane.showMessageDialog(this, "Delete sucessfully !!");
            }
        }
    }//GEN-LAST:event_DeleteUserActionPerformed

    private void tableCourseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableCourseMouseClicked
        pos = this.tableCourse.getSelectedRow();
        viewCourse();
    }//GEN-LAST:event_tableCourseMouseClicked

    private void schedulesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schedulesActionPerformed
        main.setVisible(false);
        CourseForm.setVisible(false);
        UserForm.setVisible(false);
        CoachForm.setVisible(false);
        ScheduleForm.setVisible(true);
        viewScheduleTable();
        viewNutritionTable();
        viewWorkoutTable();
    }//GEN-LAST:event_schedulesActionPerformed

    private void searchScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchScheduleActionPerformed

        if (modelSchedule.getRowCount() > 0) {
            tableSchedule.setRowSorter(null);
            TableRowSorter<TableModel> sorter = new TableRowSorter<>(modelSchedule);
            tableSchedule.setRowSorter(sorter);

            String searchText = txtsSearchID.getText();
            if (searchText.trim().isEmpty()) {
                sorter.setRowFilter(null);
            } else {
                try {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText, 0));
                } catch (java.util.regex.PatternSyntaxException e) {
                    System.out.println("Invalid search pattern.");
                }
            }
        } else {
            System.out.println("No data in table to search.");
        }
    }//GEN-LAST:event_searchScheduleActionPerformed

    private void ProgressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProgressActionPerformed
        new CoachTranning().setVisible(true);

    }//GEN-LAST:event_ProgressActionPerformed

    private void updateScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateScheduleActionPerformed
        String selectedAttribute = (String) comboBoxSchedule.getSelectedItem();
        String newValue = txtUpdate.getText();
        if (newValue.isEmpty()) {
            JOptionPane.showMessageDialog(null, " Please input you want update !!", "Error", JOptionPane.INFORMATION_MESSAGE);
        } else {
            Schedule schedule = scheduleService.findByID(txtsSearchID.getText());
            if (schedule != null) {
                switch (selectedAttribute) {
                    case "TypeUser":
                        if (newValue.equalsIgnoreCase("Beginner")) {
                            schedule.setLevel(true);
                        } else if (newValue.equalsIgnoreCase("Advanced")) {
                            schedule.setLevel(false);
                            JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid input for Level. Enter 'Beginner' or 'Advanced'.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        break;
                    case "SessionDate":
                        LocalDate targetDate = validation.convertStringToLocalDate(txtUpdate.getText());
                        if (targetDate != null) {
                            List<LocalDate> sessionDates = schedule.getSessionDate();
                            if (sessionDates.contains(targetDate)) {
                                DateInputDialog newDateDialog = new DateInputDialog(null);
                                newDateDialog.setTitle("Enter the new date (DD/MM/YYYY)");
                                newDateDialog.setVisible(true);
                                LocalDate newDate = newDateDialog.getDate();
                                if (newDate != null) {
                                    scheduleService.editDate(schedule.getScheduleID(), sessionDates, targetDate, newDate);
                                    JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);

                                } else {
                                    JOptionPane.showMessageDialog(null, "No new date entered.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "The target date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No target date entered.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    case "Time":
                        LocalDate timeTargetDate = validation.convertStringToLocalDate(txtUpdate.getText());
                        if (timeTargetDate != null) {
                            List<LocalDate> sessionDates = schedule.getSessionDate();
                            List<TimeRange> availableTimes = schedule.getAvailableTimes();
                            if (sessionDates.contains(timeTargetDate)) {
                                TimeInputDialog timeInputDialog = new TimeInputDialog(null);
                                timeInputDialog.setVisible(true);
                                LocalTime newStartTime = timeInputDialog.getStartTime();
                                double duration = timeInputDialog.getDuration();
                                if (newStartTime != null) {
                                    scheduleService.editTime(schedule.getScheduleID(), availableTimes, sessionDates, timeTargetDate, newStartTime, duration);
                                    JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "No start time or duration entered.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "The specified date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "No target date entered.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        break;

                    case "Exercises":
                        String[] ex = newValue.split(",");
                        List<String> exerciseList = new ArrayList<>();
                        for (String x : ex) {
                            exerciseList.add(x.trim());
                        }
                        List<LocalDate> sessionDates = schedule.getSessionDate();
                        DateInputDialog newDateDialog = new DateInputDialog(null);
                        newDateDialog.setTitle("Enter the date of practice day (DD/MM/YYYY)");
                        newDateDialog.setVisible(true);
                        LocalDate newDate = newDateDialog.getDate();
                        if (sessionDates.contains(newDate)) {
                            int index = sessionDates.indexOf(newDate);
                            List<List<String>> sessionExercises = schedule.getSessionExercises();
                            sessionExercises.set(index, exerciseList);
                            schedule.setSessionExercises(sessionExercises);
                            JOptionPane.showMessageDialog(null, "Update successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(null, "No date of practice day is not in list .", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                }
                scheduleService.update(schedule);
                viewScheduleTable();
            } else {
                JOptionPane.showMessageDialog(null, "ID not found !!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_updateScheduleActionPerformed

    private void comboBoxScheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxScheduleActionPerformed
        String selectedAttribute = (String) comboBoxSchedule.getSelectedItem();
        switch (selectedAttribute) {
            case "SessionDate" ->
                labelInput.setText("Old Date :");
            case "TypeUser" ->
                labelInput.setText("New TypeUser :");
            case "Time" ->
                labelInput.setText("Practice day :");
            case "Exercises" ->
                labelInput.setText("New Exercises :");

            default ->
                throw new AssertionError();
        }
    }//GEN-LAST:event_comboBoxScheduleActionPerformed
    public boolean isWorkout() {
        boolean isValid = true;
        txtsWorkoutID.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String workoutID = txtsWorkoutID.getText();
                if (workoutID.isEmpty()) {
                    workoutidHasError = true;
                    JOptionPane.showMessageDialog(null, "WorkoutID not null !!", "Error", JOptionPane.INFORMATION_MESSAGE);
                    txtsWorkoutID.requestFocus();
                } else {
                    if (validation.checkStringInputFormJFRAME(rootPane, workoutID, View.WORKOUT_REGEX) != null) {
                        workoutidHasError = false;
                    } else {
                        workoutidHasError = true;
                        JOptionPane.showMessageDialog(null, "WorkoutID not fomat . Please input fomat(WKT-YYYY) !!", "Error", JOptionPane.INFORMATION_MESSAGE);
                        txtsWorkoutID.requestFocus();
                    }
                }
            }
        });
        txtWorkoutName.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String workoutName = txtWorkoutName.getText();
                if (workoutidHasError == false) {
                    if (workoutName.isEmpty()) {
                        nameHasError = true;

                        JOptionPane.showMessageDialog(null, "Workout name not null !!", "Error", JOptionPane.INFORMATION_MESSAGE);
                        txtWorkoutName.requestFocus();
                    } else {
                        nameHasError = false;
                    }
                }
            }
        });
        txtsExercises.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String[] ex = txtsExercises.getText().split(",");
                if (workoutidHasError == false || nameHasError == false) {
                    if (ex.length > 0) {
                        phoneHasError = false;
                    } else {
                        phoneHasError = true;
                        JOptionPane.showMessageDialog(null, "Exercise not null !!", "Error", JOptionPane.INFORMATION_MESSAGE);
                        txtsExercises.requestFocus();
                    }
                }
            }
        });
        if (workoutidHasError == false && nameHasError == false && phoneHasError == false) {
            isValid = false;
        }
        return isValid;
    }

    private void AddWorkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddWorkoutActionPerformed
        String workoutID = txtsWorkoutID.getText();
        String workoutName = txtWorkoutName.getText();
        String[] ex = txtsExercises.getText().split(",");
        List<String> exList = new ArrayList<>();
        for (String c : ex) {
            exList.add(c.trim());
        }
        if (isWorkout() == false) {
            if (new WorkoutService().findByID(workoutID) != null) {
                new WorkoutService().createWorkOut(new Workout(workoutID, workoutName, exList));
                viewWorkoutTable();
                JOptionPane.showMessageDialog(null, "Add successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Workout ID exsit !!", "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_AddWorkoutActionPerformed

    private void resetWorkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetWorkoutActionPerformed
        txtsWorkoutID.setText(null);
        txtWorkoutName.setText(null);
        txtsExercises.setText(null);
    }//GEN-LAST:event_resetWorkoutActionPerformed

    private void deleteWorkoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteWorkoutActionPerformed
        String workoutID = txtsWorkoutID.getText().toUpperCase();
        if (new WorkoutService().getListWorkOut().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty list . No search can be perform !!", "Erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            Workout workout = new WorkoutService().findByID(workoutID);
            if (workout == null) {
                JOptionPane.showMessageDialog(this, "WorkoutID doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                new WorkoutService().delete(workoutID);
                viewWorkoutTable();
                JOptionPane.showMessageDialog(this, "Delete sucessfully !!");
            }
        }
    }//GEN-LAST:event_deleteWorkoutActionPerformed

    private void AddNutritionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNutritionActionPerformed
        String nutritionID = txtNutritionID1.getText();
        String[] nutrition = txtNutrition.getText().split(",");
        List<String> nutritionList = new ArrayList<>();
        for (String string : nutrition) {
            nutritionList.add(string.trim());
        }
        if (nutrition.length <= 0) {
            JOptionPane.showMessageDialog(this, "Nutrition not null", "Failure", JOptionPane.ERROR_MESSAGE);
            txtNutrition.requestFocus();
        }
        if (nutritionID.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NutritionID not null", "Failure", JOptionPane.ERROR_MESSAGE);
            txtNutritionID1.requestFocus();
        }
        if (new NutritionService().findByID(nutritionID) == null) {
            new NutritionService().create(new Nutrition(nutritionID, nutritionList));
            JOptionPane.showMessageDialog(this, "Add sucessfully !!");
            viewNutritionTable();
        } else {
            JOptionPane.showMessageDialog(this, "NutritionID exsit !!", "Failure", JOptionPane.ERROR_MESSAGE);
        }


    }//GEN-LAST:event_AddNutritionActionPerformed

    private void deleteNutritionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteNutritionActionPerformed
        String nutritionID = txtNutritionID1.getText().toUpperCase();
        if (new NutritionService().getAllNutrition().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Empty list . No search can be perform !!", "Erorr", JOptionPane.ERROR_MESSAGE);
        } else {
            Nutrition nutrition = new NutritionService().findByID(nutritionID);
            if (nutrition == null) {
                JOptionPane.showMessageDialog(this, "WorkoutID doesn't exist", "Failure", JOptionPane.ERROR_MESSAGE);
            } else {
                new NutritionService().delete(nutritionID);
                viewNutritionTable();
                JOptionPane.showMessageDialog(this, "Delete sucessfully !!");
            }
        }
    }//GEN-LAST:event_deleteNutritionActionPerformed

    private void resetNutritionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetNutritionActionPerformed
        txtNutritionID1.setText(null);
        txtNutrition.setText(null);
    }//GEN-LAST:event_resetNutritionActionPerformed

    private void getMaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMaleActionPerformed
         if (getMale.isSelected()) {
                    getFemale.setEnabled(false); 
                } else {
                    getFemale.setEnabled(true);  
                }
    }//GEN-LAST:event_getMaleActionPerformed

    private void getFemaleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFemaleActionPerformed
 if (getFemale.isSelected()) {
                    getMale.setEnabled(false); 
                } else {
                    getMale.setEnabled(true);  
                }
    }//GEN-LAST:event_getFemaleActionPerformed

    private void getMale1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getMale1ActionPerformed
if (getMale1.isSelected()) {
                    getFemale1.setEnabled(false); 
                } else {
                    getFemale1.setEnabled(true);  
                }
    }//GEN-LAST:event_getMale1ActionPerformed

    private void getFemale1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFemale1ActionPerformed
         if (getFemale1.isSelected()) {
                    getMale1.setEnabled(false); 
                } else {
                    getMale1.setEnabled(true);  
                } 
    }//GEN-LAST:event_getFemale1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FitnessForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FitnessForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FitnessForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FitnessForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FitnessForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNutrition;
    private javax.swing.JButton AddWorkout;
    private javax.swing.JPanel CoachForm;
    private javax.swing.JButton Course;
    private javax.swing.JPanel CourseForm;
    private javax.swing.JButton DeleteUser;
    private javax.swing.JButton Progress;
    private javax.swing.JLabel SSN;
    private javax.swing.JPanel ScheduleForm;
    private javax.swing.JButton User;
    private javax.swing.JPanel UserForm;
    private javax.swing.JButton UserUpdate;
    private javax.swing.JButton add;
    private javax.swing.JButton add1;
    private javax.swing.JPanel background;
    private javax.swing.JButton coach;
    private javax.swing.JComboBox<String> comboBoxSchedule;
    private javax.swing.JPanel control;
    private javax.swing.JButton delete;
    private javax.swing.JButton delete1;
    private javax.swing.JButton deleteNutrition;
    private javax.swing.JButton deleteWorkout;
    private javax.swing.JComboBox<String> getDay;
    private javax.swing.JComboBox<String> getDay1;
    private javax.swing.JRadioButton getFemale;
    private javax.swing.JRadioButton getFemale1;
    private javax.swing.JRadioButton getMale;
    private javax.swing.JRadioButton getMale1;
    private javax.swing.JComboBox<String> getMonth;
    private javax.swing.JComboBox<String> getMonth1;
    private javax.swing.JComboBox<String> getYear;
    private javax.swing.JComboBox<String> getYear1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel labelCoach;
    private javax.swing.JLabel labelInput;
    private javax.swing.JLabel labelMember;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel labelTotal;
    private javax.swing.JComboBox<String> levelbox;
    private javax.swing.JButton logout;
    private javax.swing.JPanel main;
    private javax.swing.JButton reset;
    private javax.swing.JButton reset1;
    private javax.swing.JButton resetNutrition;
    private javax.swing.JButton resetUser;
    private javax.swing.JButton resetWorkout;
    private javax.swing.JButton schedules;
    private javax.swing.JButton searchSchedule;
    private javax.swing.JTable tableCoach;
    private javax.swing.JTable tableCourse;
    private javax.swing.JTable tableNutrition;
    private javax.swing.JTable tableSchedule;
    private javax.swing.JTable tableUser;
    private javax.swing.JTable tableWorkout;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtCoachID;
    private javax.swing.JTextField txtCoachID1;
    private javax.swing.JTextField txtCourseID;
    private javax.swing.JTextField txtCourseName;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JLabel txtID;
    private javax.swing.JLabel txtID1;
    private javax.swing.JLabel txtID10;
    private javax.swing.JLabel txtID11;
    private javax.swing.JLabel txtID12;
    private javax.swing.JLabel txtID13;
    private javax.swing.JLabel txtID14;
    private javax.swing.JLabel txtID15;
    private javax.swing.JLabel txtID16;
    private javax.swing.JLabel txtID17;
    private javax.swing.JLabel txtID18;
    private javax.swing.JLabel txtID19;
    private javax.swing.JLabel txtID2;
    private javax.swing.JLabel txtID20;
    private javax.swing.JLabel txtID21;
    private javax.swing.JLabel txtID22;
    private javax.swing.JLabel txtID3;
    private javax.swing.JLabel txtID4;
    private javax.swing.JLabel txtID5;
    private javax.swing.JLabel txtID6;
    private javax.swing.JLabel txtID7;
    private javax.swing.JLabel txtID8;
    private javax.swing.JLabel txtID9;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtNutrition;
    private javax.swing.JTextField txtNutritionID;
    private javax.swing.JTextField txtNutritionID1;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtSSN;
    private javax.swing.JTextField txtSalary;
    private javax.swing.JTextField txtTotalWeek;
    private javax.swing.JTextField txtUpdate;
    private javax.swing.JTextField txtUserAddress;
    private javax.swing.JTextField txtUserEmail;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserPhone;
    private javax.swing.JTextField txtUserSSN;
    private javax.swing.JTextField txtWeight;
    private javax.swing.JTextField txtWorkOutID;
    private javax.swing.JTextField txtWorkoutName;
    private javax.swing.JLabel txtgender;
    private javax.swing.JLabel txtgender1;
    private javax.swing.JLabel txtgender2;
    private javax.swing.JLabel txtgender3;
    private javax.swing.JTextField txtheight;
    private javax.swing.JTextField txtsExercises;
    private javax.swing.JTextField txtsSearchID;
    private javax.swing.JTextField txtsWorkoutID;
    private javax.swing.JButton update;
    private javax.swing.JButton update1;
    private javax.swing.JButton updateSchedule;
    // End of variables declaration//GEN-END:variables
}

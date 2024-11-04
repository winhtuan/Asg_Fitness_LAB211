package viewSwing;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import model.Address;
import model.Coach;
import model.Course;
import model.Nutrition;
import model.Schedule;
import model.Users;
import model.Workout;
import service.CoachService;
import service.CourseRegistration;
import service.CourseService;
import service.NutritionService;
import service.ScheduleService;
import service.UserService;
import service.WorkoutService;
import util.Validation;

public class CustomerForm extends javax.swing.JFrame {

    private DefaultTableModel modelCourse;
    private DefaultTableModel modelMyCourse;
    private DefaultTableModel modelschedule;

    private CourseService courseService = new CourseService();
    private Login login = new Login();
    private ScheduleService scheduleService = new ScheduleService();

    public CustomerForm() {
        initComponents();
        setLocationRelativeTo(null);
        modelCourse = (DefaultTableModel) tableCourse.getModel();
        modelMyCourse = (DefaultTableModel) tableMyCourse.getModel();
        modelschedule = (DefaultTableModel) tableSchedule.getModel();
        labelName();

    }

    public void labelName() {
        String userID = login.getUserID();
        if (userID != null && !userID.trim().isEmpty()) {
            Users user = new UserService().findByID(userID.trim());
            if (user != null) {
                labelName.setText(user.getName());
            } else {
                labelName.setText("Guest");
            }
        } else {
            labelName.setText("Guest");
        }
    }

    private void showNotificationsAfterFormLoad(String courseID) {
        String userID = login.getUserID();
        if (userID != null && !userID.trim().isEmpty()) {
            new ScheduleService().notifySession(userID, courseID);
        }
    }

    public void viewUser() {
        Users user = new UserService().findByID(login.getUserID());

        this.txtUserID2.setText(user.getId());
        this.txtName.setText(user.getName());
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
        this.getMale.setSelected(user.isSex());
        this.getFemale.setSelected(!user.isSex());
        this.txtHeight.setText(String.format("%.2f", user.getHeight()));
        this.txtWeight.setText(String.format("%.2f", user.getWeight()));
    }

    public void viewCourseTable() {
        modelCourse.setRowCount(0);

        List<Course> courses = courseService.getListCourse();
        for (Course course : courses) {
            Coach coach = new CoachService().findByID(course.getCoachID());
            String coachName = (coach != null) ? coach.getName() : "N/A"; // Nếu coach là null, hiển thị "N/A"
            Object[] row = {
                course.getCourseId(),
                course.getCourseName(),
                coachName,
                course.getPrice(),
                course.getDescription()
            };
            modelCourse.addRow(row);
        }
    }

    public void viewUserText() {
        String userID = login.getUserID();
        if (userID != null && !userID.trim().isEmpty()) {
            Users user = new UserService().findByID(userID.trim());
            if (user != null) {
                txtUserID.setText(user.getId());
                txtUserName.setText(user.getName());
            } else {
                txtUserName.setText("Guest");
            }
        } else {
            txtUserName.setText("Guest");
        }
    }

    public void viewScheduleTable() {
        modelschedule.setNumRows(0);
        for (Schedule schedules : scheduleService.getAllSchedule()) {
            if (schedules.getCusID().equalsIgnoreCase(login.getUserID()) && schedules.getCourseID().equalsIgnoreCase(viewCourseID.getText())) {

                for (int i = 0; i < schedules.getSessionDate().size(); i++) {
                    Object[] rowData = {
                        schedules.getCourseID(),
                        schedules.getSessionDate().get(i).format(service.Service.DATE_FORMAT),
                        schedules.getAvailableTimes().get(i).getStart(),
                        schedules.getAvailableTimes().get(i).getEnd(),
                        String.join(", ", schedules.getSessionExercises().get(i))
                    };
                    modelschedule.addRow(rowData);
                }
            }
        }
    }

    public void viewMyCourseTable() {
        modelMyCourse.setRowCount(0);
        for (Schedule myCourse : scheduleService.getAllSchedule()) {
            Course course = new CourseService().findByID(myCourse.getCourseID());
            Coach coach = new CoachService().findByID(course.getCoachID());
            Workout workout = new WorkoutService().findByID(course.getWorkoutID());
            Nutrition nutrition = new NutritionService().findByID(course.getNutritionID());
            if (myCourse.getCusID().equalsIgnoreCase(login.getUserID())) {
                Object[] row = {
                    myCourse.getCourseID(),
                    course.getCourseName(),
                    coach.getName(),
                    workout.getWorkoutName(),
                    nutrition.getNutriList()
                };
                modelMyCourse.addRow(row);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        control = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        schedule = new javax.swing.JButton();
        AllCourse = new javax.swing.JButton();
        logout = new javax.swing.JButton();
        MyCourse = new javax.swing.JButton();
        Information = new javax.swing.JButton();
        labelName = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        content = new javax.swing.JPanel();
        main = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        Schedule = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtUserID = new javax.swing.JTextField();
        txtUserName = new javax.swing.JTextField();
        viewCourseID = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        viewWorkoutID = new javax.swing.JTextField();
        viewNutritionID = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnEdit = new javax.swing.JButton();
        btnWkDetails = new javax.swing.JButton();
        btnNutDetails = new javax.swing.JButton();
        searchSchedule5 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableSchedule = new javax.swing.JTable();
        ShowMyCourse = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tableMyCourse = new javax.swing.JTable();
        ShowCourse = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableCourse = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        Register = new javax.swing.JButton();
        CourseID = new javax.swing.JLabel();
        txtCourseID = new javax.swing.JTextField();
        startdate = new javax.swing.JLabel();
        level = new javax.swing.JLabel();
        cbLevel = new javax.swing.JComboBox<>();
        getDay = new javax.swing.JComboBox<>();
        getMonth = new javax.swing.JComboBox<>();
        BestCourse = new javax.swing.JButton();
        Infor = new javax.swing.JPanel();
        txtID5 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtUserID2 = new javax.swing.JTextField();
        txtID8 = new javax.swing.JLabel();
        txtID9 = new javax.swing.JLabel();
        txtUserPhone = new javax.swing.JTextField();
        txtID10 = new javax.swing.JLabel();
        txtUserEmail = new javax.swing.JTextField();
        txtWeight = new javax.swing.JTextField();
        txtgender1 = new javax.swing.JLabel();
        getMale = new javax.swing.JRadioButton();
        getFemale = new javax.swing.JRadioButton();
        txtgender3 = new javax.swing.JLabel();
        getDay1 = new javax.swing.JComboBox<>();
        getMonth1 = new javax.swing.JComboBox<>();
        getYear1 = new javax.swing.JComboBox<>();
        txtID11 = new javax.swing.JLabel();
        txtUserAddress = new javax.swing.JTextField();
        txtID12 = new javax.swing.JLabel();
        txtID13 = new javax.swing.JLabel();
        txtHeight = new javax.swing.JTextField();
        SSN = new javax.swing.JLabel();
        txtUserSSN = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        control.setBackground(new java.awt.Color(68, 222, 222));
        control.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(242, 242, 242));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/schedule_8161423.png"))); // NOI18N
        jLabel1.setText("Fitness Management System");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user_1077114.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(242, 242, 242));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Welcome");

        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        schedule.setBackground(new java.awt.Color(204, 255, 255));
        schedule.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        schedule.setForeground(new java.awt.Color(0, 153, 153));
        schedule.setText("Schedules");
        schedule.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        schedule.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scheduleActionPerformed(evt);
            }
        });

        AllCourse.setBackground(new java.awt.Color(204, 255, 255));
        AllCourse.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        AllCourse.setForeground(new java.awt.Color(0, 153, 153));
        AllCourse.setText("All Course");
        AllCourse.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        AllCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllCourseActionPerformed(evt);
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

        MyCourse.setBackground(new java.awt.Color(204, 255, 255));
        MyCourse.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        MyCourse.setForeground(new java.awt.Color(0, 153, 153));
        MyCourse.setText("My Course");
        MyCourse.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        MyCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MyCourseActionPerformed(evt);
            }
        });

        Information.setBackground(new java.awt.Color(204, 255, 255));
        Information.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Information.setForeground(new java.awt.Color(0, 153, 153));
        Information.setText("My Profile");
        Information.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Information.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                InformationActionPerformed(evt);
            }
        });

        labelName.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        labelName.setForeground(new java.awt.Color(242, 242, 242));
        labelName.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelName.setText("Guest");

        javax.swing.GroupLayout controlLayout = new javax.swing.GroupLayout(control);
        control.setLayout(controlLayout);
        controlLayout.setHorizontalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(controlLayout.createSequentialGroup()
                        .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(controlLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(23, 23, 23)
                                .addComponent(logout, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(AllCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(schedule, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MyCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Information, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(controlLayout.createSequentialGroup()
                                .addGap(112, 112, 112)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(controlLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSeparator1)))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        controlLayout.setVerticalGroup(
            controlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(controlLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(1, 1, 1)
                .addComponent(labelName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(Information, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43)
                .addComponent(schedule, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(MyCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(AllCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(66, 66, 66)
                .addComponent(logout)
                .addGap(27, 27, 27))
        );

        jSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        content.setLayout(new java.awt.CardLayout());

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/3.jpg"))); // NOI18N

        javax.swing.GroupLayout mainLayout = new javax.swing.GroupLayout(main);
        main.setLayout(mainLayout);
        mainLayout.setHorizontalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainLayout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainLayout.setVerticalGroup(
            mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 724, Short.MAX_VALUE)
        );

        content.add(main, "card2");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setBackground(new java.awt.Color(153, 255, 255));
        jLabel5.setFont(new java.awt.Font("SansSerif", 1, 24)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("PERSONAL SCHEDULE");

        jLabel6.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel6.setText("User ID");

        jLabel7.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel7.setText("User Name");

        jLabel9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel9.setText("Course ID");

        txtUserID.setEditable(false);
        txtUserID.setBackground(new java.awt.Color(255, 255, 255));
        txtUserID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        txtUserName.setEditable(false);
        txtUserName.setBackground(new java.awt.Color(255, 255, 255));
        txtUserName.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        viewCourseID.setBackground(new java.awt.Color(255, 255, 255));
        viewCourseID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        viewCourseID.setVerifyInputWhenFocusTarget(false);

        jLabel10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel10.setText("Workout ID");

        viewWorkoutID.setEditable(false);
        viewWorkoutID.setBackground(new java.awt.Color(255, 255, 255));
        viewWorkoutID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        viewNutritionID.setEditable(false);
        viewNutritionID.setBackground(new java.awt.Color(255, 255, 255));
        viewNutritionID.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N

        jLabel11.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jLabel11.setText("Nutrition ID");

        jLabel8.setBackground(new java.awt.Color(153, 255, 255));
        jLabel8.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Schedule Info");

        btnEdit.setBackground(new java.awt.Color(255, 204, 204));
        btnEdit.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnEdit.setText("Edit Schedule");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnWkDetails.setBackground(new java.awt.Color(255, 255, 204));
        btnWkDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnWkDetails.setText("Workout Details");
        btnWkDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWkDetailsActionPerformed(evt);
            }
        });

        btnNutDetails.setBackground(new java.awt.Color(204, 255, 204));
        btnNutDetails.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        btnNutDetails.setText("Nutrition Details");
        btnNutDetails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNutDetailsActionPerformed(evt);
            }
        });

        searchSchedule5.setBackground(new java.awt.Color(204, 255, 255));
        searchSchedule5.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        searchSchedule5.setForeground(new java.awt.Color(0, 153, 153));
        searchSchedule5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/searching (1).png"))); // NOI18N
        searchSchedule5.setText("Search");
        searchSchedule5.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        searchSchedule5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchSchedule5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 642, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(52, 52, 52)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(28, 28, 28)
                                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(103, 103, 103))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(searchSchedule5, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(viewCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(43, 43, 43)
                                        .addComponent(viewWorkoutID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(41, 41, 41)
                                        .addComponent(viewNutritionID, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(btnNutDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(1, 1, 1))
                                    .addComponent(btnWkDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57))))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUserID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnWkDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addComponent(btnNutDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(viewWorkoutID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(viewCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11)
                                .addComponent(searchSchedule5, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(viewNutritionID))
                        .addGap(17, 17, 17))))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableSchedule.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableSchedule.setFont(new java.awt.Font("SansSerif", 0, 13)); // NOI18N
        tableSchedule.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CourseID", "Date", "Start Time", "End Time", "Exercise"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tableSchedule);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout ScheduleLayout = new javax.swing.GroupLayout(Schedule);
        Schedule.setLayout(ScheduleLayout);
        ScheduleLayout.setHorizontalGroup(
            ScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScheduleLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ScheduleLayout.setVerticalGroup(
            ScheduleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ScheduleLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        content.add(Schedule, "card3");

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/4800.jpg"))); // NOI18N
        jLabel12.setText("jLabel12");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel22.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/treadmill.png"))); // NOI18N
        jLabel22.setText("USER COURSE");

        tableMyCourse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableMyCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "CourseID", "CourseName", "CoachName", "WorkoutName", "Nutrition"
            }
        ));
        jScrollPane3.setViewportView(tableMyCourse);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 852, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(212, 212, 212)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout ShowMyCourseLayout = new javax.swing.GroupLayout(ShowMyCourse);
        ShowMyCourse.setLayout(ShowMyCourseLayout);
        ShowMyCourseLayout.setHorizontalGroup(
            ShowMyCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ShowMyCourseLayout.setVerticalGroup(
            ShowMyCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowMyCourseLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        content.add(ShowMyCourse, "card4");

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        tableCourse.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        tableCourse.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        tableCourse.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Course ID", "Course Name", "Coach Name", "Price", "Discription"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tableCourse);
        if (tableCourse.getColumnModel().getColumnCount() > 0) {
            tableCourse.getColumnModel().getColumn(0).setPreferredWidth(40);
            tableCourse.getColumnModel().getColumn(3).setPreferredWidth(40);
            tableCourse.getColumnModel().getColumn(4).setPreferredWidth(200);
        }

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/footter.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(15, Short.MAX_VALUE))
        );

        jSeparator2.setForeground(new java.awt.Color(0, 0, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        Register.setBackground(new java.awt.Color(204, 255, 255));
        Register.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        Register.setForeground(new java.awt.Color(0, 153, 153));
        Register.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/icon_registration.png"))); // NOI18N
        Register.setText("Register");
        Register.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterActionPerformed(evt);
            }
        });

        CourseID.setBackground(new java.awt.Color(255, 255, 255));
        CourseID.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        CourseID.setText("CourseID");

        txtCourseID.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N

        startdate.setBackground(new java.awt.Color(255, 255, 255));
        startdate.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        startdate.setText("Start Date");

        level.setBackground(new java.awt.Color(255, 255, 255));
        level.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        level.setText("Level");

        cbLevel.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        cbLevel.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Beginner", "Advanced" }));

        getDay.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        getDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31" }));
        getDay.setSelectedIndex(15);

        getMonth.setFont(new java.awt.Font("SansSerif", 0, 15)); // NOI18N
        getMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" }));
        getMonth.setSelectedIndex(10);

        BestCourse.setBackground(new java.awt.Color(255, 204, 204));
        BestCourse.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        BestCourse.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/1.png"))); // NOI18N
        BestCourse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BestCourseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 226, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(CourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cbLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(61, 61, 61)
                        .addComponent(getDay, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(101, 101, 101)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Register, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                    .addComponent(BestCourse, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(108, 108, 108))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(CourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCourseID, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Register, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(level, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbLevel))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(getDay, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(getMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(startdate, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(BestCourse, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout ShowCourseLayout = new javax.swing.GroupLayout(ShowCourse);
        ShowCourse.setLayout(ShowCourseLayout);
        ShowCourseLayout.setHorizontalGroup(
            ShowCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(ShowCourseLayout.createSequentialGroup()
                .addComponent(jSeparator2)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        ShowCourseLayout.setVerticalGroup(
            ShowCourseLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ShowCourseLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        content.add(ShowCourse, "card5");

        Infor.setBackground(new java.awt.Color(255, 255, 255));
        Infor.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtID5.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID5.setText("User ID");
        txtID5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtName.setEditable(false);

        txtUserID2.setEditable(false);

        txtID8.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID8.setText("Name");
        txtID8.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID9.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID9.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID9.setText("Phone");
        txtID9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtUserPhone.setEditable(false);
        txtUserPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserPhoneActionPerformed(evt);
            }
        });

        txtID10.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID10.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID10.setText("Email");
        txtID10.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtUserEmail.setEditable(false);

        txtWeight.setEditable(false);

        txtgender1.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtgender1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtgender1.setText("Gender");
        txtgender1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

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

        txtUserAddress.setEditable(false);

        txtID12.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID12.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID12.setText("Height");
        txtID12.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtID13.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        txtID13.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        txtID13.setText("Weight");
        txtID13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtHeight.setEditable(false);

        SSN.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        SSN.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        SSN.setText("SSN");
        SSN.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        txtUserSSN.setEditable(false);
        txtUserSSN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUserSSNActionPerformed(evt);
            }
        });

        jLabel18.setBackground(new java.awt.Color(255, 255, 255));
        jLabel18.setFont(new java.awt.Font("SansSerif", 1, 16)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/user.png"))); // NOI18N
        jLabel18.setText("INFORMATION");

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/freepik__expand__19774 (1).png"))); // NOI18N

        javax.swing.GroupLayout InforLayout = new javax.swing.GroupLayout(Infor);
        Infor.setLayout(InforLayout);
        InforLayout.setHorizontalGroup(
            InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InforLayout.createSequentialGroup()
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InforLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtID10, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(InforLayout.createSequentialGroup()
                                .addComponent(SSN, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtUserSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InforLayout.createSequentialGroup()
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InforLayout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(txtID5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InforLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(txtID8, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(24, 24, 24)))
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserID2, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InforLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(InforLayout.createSequentialGroup()
                                .addComponent(txtID9, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtUserPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(54, 54, 54)
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InforLayout.createSequentialGroup()
                        .addComponent(txtgender1, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)
                        .addComponent(getMale, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(getFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(InforLayout.createSequentialGroup()
                        .addComponent(txtID13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserAddress)
                            .addGroup(InforLayout.createSequentialGroup()
                                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(InforLayout.createSequentialGroup()
                                        .addComponent(getDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(getMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(getYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 99, Short.MAX_VALUE)))
                        .addGap(95, 95, 95))
                    .addGroup(InforLayout.createSequentialGroup()
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtID11, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                .addComponent(txtID12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(txtgender3))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(InforLayout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );
        InforLayout.setVerticalGroup(
            InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InforLayout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtID5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserID2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtgender1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getMale, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getFemale, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtgender3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(getDay1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getMonth1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(getYear1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtName, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InforLayout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtID9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtUserPhone, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(InforLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtUserAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtID11, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtHeight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtID12, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtUserEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtID10, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(28, 28, 28)
                .addGroup(InforLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtWeight, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtID13, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUserSSN, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(SSN, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(215, 215, 215))
        );

        content.add(Infor, "card6");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(content, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(309, 309, 309)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(867, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(content, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addComponent(control, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator4, javax.swing.GroupLayout.DEFAULT_SIZE, 712, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void AllCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AllCourseActionPerformed
        Infor.setVisible(false);
        ShowCourse.setVisible(true);
        main.setVisible(false);
        ShowMyCourse.setVisible(false);
        Schedule.setVisible(false);
        viewCourseTable();
    }//GEN-LAST:event_AllCourseActionPerformed

    private void logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logoutActionPerformed
        this.setVisible(false);
        new Login().setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }//GEN-LAST:event_logoutActionPerformed

    private void MyCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MyCourseActionPerformed
        if ("guest".equals(login.getUserRole())) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "You need to log in to access this feature. Would you like to log in?",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login().setVisible(true);
            }
        } else {
            Infor.setVisible(false);
            ShowCourse.setVisible(false);
            main.setVisible(false);
            ShowMyCourse.setVisible(true);
            Schedule.setVisible(false);
            viewMyCourseTable();
        }
    }//GEN-LAST:event_MyCourseActionPerformed

    private void scheduleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scheduleActionPerformed
        if ("guest".equals(login.getUserRole())) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "You need to log in to access this feature. Would you like to log in?",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login().setVisible(true);
            }
        } else {
            Infor.setVisible(false);
            ShowCourse.setVisible(false);
            main.setVisible(false);
            ShowMyCourse.setVisible(false);
            Schedule.setVisible(true);
            viewUserText();
        }
    }//GEN-LAST:event_scheduleActionPerformed

    private void RegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterActionPerformed
        if ("guest".equals(login.getUserRole())) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "You need to log in to access this feature. Would you like to log in?",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login().setVisible(true);
            }
        } else {
            String courseID = txtCourseID.getText();
            boolean isBeginner = "Beginner".equals(cbLevel.getSelectedItem());
            LocalDate startDate = new Validation().convertStringToLocalDate(getDay.getSelectedItem().toString() + "/" + getMonth.getSelectedItem().toString() + "/2024");
            new CourseRegistration().registerCourse(login.getUserID(), courseID, isBeginner, startDate);
        }
    }//GEN-LAST:event_RegisterActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        Schedule currentSchedule = scheduleService.findScheduleByUserID(login.getUserID());
        EditScheduleDialog editDialog = new EditScheduleDialog(this, currentSchedule, scheduleService);
        editDialog.setVisible(true);
        viewScheduleTable();
    }//GEN-LAST:event_btnEditActionPerformed

    private void BestCourseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BestCourseActionPerformed
        Map<String, Integer> courseCountMap = new HashMap<>();
        for (Schedule b : scheduleService.getAllSchedule()) {
            String courseID = b.getCourseID();
            courseCountMap.put(courseID, courseCountMap.getOrDefault(courseID, 0) + 1);
        }
        String bestCourse = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : courseCountMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                bestCourse = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        modelCourse.setRowCount(0);
        List<Course> courses = courseService.getListCourse();
        for (Course course : courses) {
            if (course.getCourseId().equalsIgnoreCase(bestCourse)) {
                Coach coach = new CoachService().findByID(course.getCoachID());
                Object[] row = {
                    course.getCourseId(),
                    course.getCourseName(),
                    coach.getName(),
                    course.getPrice(),
                    course.getDescription()
                };
                modelCourse.addRow(row);
            }
        }
    }//GEN-LAST:event_BestCourseActionPerformed

    private void InformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_InformationActionPerformed
        if ("guest".equals(login.getUserRole())) {
            int choice = JOptionPane.showConfirmDialog(null,
                    "You need to log in to access this feature. Would you like to log in?",
                    "Login Required",
                    JOptionPane.YES_NO_OPTION);

            if (choice == JOptionPane.YES_OPTION) {
                this.setVisible(false);
                new Login().setVisible(true);
            }
        } else {
            Infor.setVisible(true);
            ShowCourse.setVisible(false);
            main.setVisible(false);
            ShowMyCourse.setVisible(false);
            Schedule.setVisible(false);
            viewUser();
        }
    }//GEN-LAST:event_InformationActionPerformed

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

    private void txtUserPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserPhoneActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserPhoneActionPerformed

    private void txtUserSSNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserSSNActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserSSNActionPerformed

    private void btnWkDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWkDetailsActionPerformed
        Schedule s = new ScheduleService().findScheduleByUserID(login.getUserID());
        Workout workout = new WorkoutService().findByID(s.getWorkoutID());
        List<String> wkList = workout.getExercises();
        WorkoutDialog workoutDialog = new WorkoutDialog(this, wkList);
        workoutDialog.showDialog();
    }//GEN-LAST:event_btnWkDetailsActionPerformed

    private void btnNutDetailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNutDetailsActionPerformed
        Schedule s = new ScheduleService().findScheduleByUserID(login.getUserID());
        Course course = courseService.findByID(s.getCourseID());
        Nutrition nutrition = new NutritionService().findByID(course.getNutritionID());
        List<String> nutList = nutrition.getNutriList();
        NutritionDialog nutritionDialog = new NutritionDialog(this, nutList);
        nutritionDialog.showDialog();
    }//GEN-LAST:event_btnNutDetailsActionPerformed

    private void searchSchedule5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchSchedule5ActionPerformed
        SwingUtilities.invokeLater(() -> showNotificationsAfterFormLoad(viewCourseID.getText()));
        for (Schedule schedule1 : scheduleService.getAllSchedule()) {
            if (schedule1.getCusID().equalsIgnoreCase(login.getUserID()) && schedule1.getCourseID().equalsIgnoreCase(viewCourseID.getText())) {
                viewCourseID.setText(schedule1.getCourseID());
                viewNutritionID.setText(new CourseService().findByID(schedule1.getCourseID()).getNutritionID());
                viewWorkoutID.setText(schedule1.getWorkoutID());
            }
        }
        viewScheduleTable();
    }//GEN-LAST:event_searchSchedule5ActionPerformed

    /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(CustomerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CustomerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CustomerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CustomerForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CustomerForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AllCourse;
    private javax.swing.JButton BestCourse;
    private javax.swing.JLabel CourseID;
    private javax.swing.JPanel Infor;
    private javax.swing.JButton Information;
    private javax.swing.JButton MyCourse;
    private javax.swing.JButton Register;
    private javax.swing.JLabel SSN;
    private javax.swing.JPanel Schedule;
    private javax.swing.JPanel ShowCourse;
    private javax.swing.JPanel ShowMyCourse;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnNutDetails;
    private javax.swing.JButton btnWkDetails;
    private javax.swing.JComboBox<String> cbLevel;
    private javax.swing.JPanel content;
    private javax.swing.JPanel control;
    private javax.swing.JComboBox<String> getDay;
    private javax.swing.JComboBox<String> getDay1;
    private javax.swing.JRadioButton getFemale;
    private javax.swing.JRadioButton getMale;
    private javax.swing.JComboBox<String> getMonth;
    private javax.swing.JComboBox<String> getMonth1;
    private javax.swing.JComboBox<String> getYear1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JLabel labelName;
    private javax.swing.JLabel level;
    private javax.swing.JButton logout;
    private javax.swing.JPanel main;
    private javax.swing.JButton schedule;
    private javax.swing.JButton searchSchedule5;
    private javax.swing.JLabel startdate;
    private javax.swing.JTable tableCourse;
    private javax.swing.JTable tableMyCourse;
    private javax.swing.JTable tableSchedule;
    private javax.swing.JTextField txtCourseID;
    private javax.swing.JTextField txtHeight;
    private javax.swing.JLabel txtID10;
    private javax.swing.JLabel txtID11;
    private javax.swing.JLabel txtID12;
    private javax.swing.JLabel txtID13;
    private javax.swing.JLabel txtID5;
    private javax.swing.JLabel txtID8;
    private javax.swing.JLabel txtID9;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtUserAddress;
    private javax.swing.JTextField txtUserEmail;
    private javax.swing.JTextField txtUserID;
    private javax.swing.JTextField txtUserID2;
    private javax.swing.JTextField txtUserName;
    private javax.swing.JTextField txtUserPhone;
    private javax.swing.JTextField txtUserSSN;
    private javax.swing.JTextField txtWeight;
    private javax.swing.JLabel txtgender1;
    private javax.swing.JLabel txtgender3;
    private javax.swing.JTextField viewCourseID;
    private javax.swing.JTextField viewNutritionID;
    private javax.swing.JTextField viewWorkoutID;
    // End of variables declaration//GEN-END:variables
}

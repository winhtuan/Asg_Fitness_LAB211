package viewSwing;

import java.util.prefs.Preferences;
import javax.swing.JOptionPane;
import service.LoginService;
import view.View;

public class Login extends javax.swing.JFrame {

    public static String userRole;
    public static String userID;

    public Preferences pref = Preferences.userRoot().node("Rememberme");

    public Login() {
        initComponents();
        String usr = null;
        usr = pref.get("user", usr);
        txtUsername.setText(usr);
        String pas = null;
        pas = pref.get("password", pas);
        txtPassword.setText(pas);
        pack();
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        Login.userID = userID;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        Login.userRole = userRole;
    }

    public void saveLogin(String mail, String password) {
        pref.put("user", mail);
        pref.put("password", password);
        System.out.println("sucessfully");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtUsername = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        login = new javax.swing.JButton();
        remember = new javax.swing.JCheckBox();
        forgotpassword = new javax.swing.JLabel();
        register = new javax.swing.JButton();
        guest = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(68, 222, 222));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtUsername.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("SansSerif", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(68, 222, 222));
        jLabel1.setText("MEMBER LOGIN");

        txtPassword.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel2.setText("Username : ");

        jLabel3.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        jLabel3.setText("Password : ");

        login.setBackground(new java.awt.Color(204, 255, 255));
        login.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        login.setForeground(new java.awt.Color(0, 153, 153));
        login.setIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\icon_login.png")); // NOI18N
        login.setText("Login");
        login.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        login.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginActionPerformed(evt);
            }
        });

        remember.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        remember.setForeground(new java.awt.Color(153, 153, 153));
        remember.setText("Remember me");
        remember.setBorder(null);
        remember.setIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\check_1828697.png")); // NOI18N
        remember.setRolloverIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\check_1828697.png")); // NOI18N
        remember.setSelectedIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\check_1828640 (1).png")); // NOI18N
        remember.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rememberActionPerformed(evt);
            }
        });

        forgotpassword.setFont(new java.awt.Font("SansSerif", 3, 12)); // NOI18N
        forgotpassword.setForeground(new java.awt.Color(68, 222, 222));
        forgotpassword.setText("Forgot Password ?");
        forgotpassword.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                forgotpasswordMouseClicked(evt);
            }
        });

        register.setBackground(new java.awt.Color(204, 255, 255));
        register.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        register.setForeground(new java.awt.Color(0, 153, 153));
        register.setIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\icon_registration.png")); // NOI18N
        register.setText("Register");
        register.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        register.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        register.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                registerActionPerformed(evt);
            }
        });

        guest.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        guest.setForeground(new java.awt.Color(255, 255, 255));
        guest.setIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\user_4302027.png")); // NOI18N
        guest.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        guest.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        guest.setDisabledIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\profile_7310896.png")); // NOI18N
        guest.setRolloverIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\user_4302027.png")); // NOI18N
        guest.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guestActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("SansSerif", 0, 12)); // NOI18N
        jLabel5.setText("Or login with");

        jPanel2.setBackground(new java.awt.Color(68, 222, 222));

        jLabel8.setIcon(new javax.swing.ImageIcon("D:\\LAB211\\project\\icon\\exercise_7091101 (1).png")); // NOI18N

        jLabel6.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(242, 242, 242));
        jLabel6.setText("MANAGEMENT");

        jLabel7.setFont(new java.awt.Font("SansSerif", 1, 48)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(242, 242, 242));
        jLabel7.setText("FITNESS ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(104, 104, 104))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(42, 42, 42))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(remember)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(forgotpassword))
                                        .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(165, 165, 165))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(224, 224, 224)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(guest))))
                .addContainerGap(75, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(remember)
                    .addComponent(forgotpassword))
                .addGap(39, 39, 39)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(login, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(register, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(guest)
                        .addGap(44, 44, 44))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(52, 52, 52))))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        txtUsername.getAccessibleContext().setAccessibleName("UserName");
        txtUsername.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void registerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_registerActionPerformed
        this.setVisible(false);
        new RegisterJFrame().setVisible(true);
    }//GEN-LAST:event_registerActionPerformed

    private void guestActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guestActionPerformed
        setUserRole("guest");
        System.out.println(getUserRole());
        this.setVisible(false);
        new CustomerForm().setVisible(true);
    }//GEN-LAST:event_guestActionPerformed

    private void rememberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rememberActionPerformed
        String password = new String(txtPassword.getPassword());
        if (remember.isSelected()) {
            saveLogin(txtUsername.getText(), password);
            JOptionPane.showMessageDialog(this, "Login information is remember !!");
        } else {
            pref.remove("user");
            pref.remove("password");
        }
    }//GEN-LAST:event_rememberActionPerformed

    private void forgotpasswordMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_forgotpasswordMouseClicked
        this.setVisible(false);
        new ForgotPassword().setVisible(true);
    }//GEN-LAST:event_forgotpasswordMouseClicked

    private void loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginActionPerformed
        String userName = txtUsername.getText();
        String password = new String(txtPassword.getPassword());
        StringBuilder sb = new StringBuilder();
        if (userName.isEmpty()) {
            sb.append("Username can't empty !! \n");
        }
        if (password.isEmpty()) {
            sb.append("Password can't empty !! \n");
        }
        if (sb.length() > 0) {
            JOptionPane.showMessageDialog(this, sb.toString(), "Invalition", JOptionPane.ERROR_MESSAGE);
            return;
        }
        model.Login loginTK = new LoginService().findByID(userName, password);
        if (loginTK != null) {
            if (userName.matches(View.USER_REGEX)) {
                setUserRole("user");
                setUserID(userName);
                JOptionPane.showMessageDialog(this, "Login sucessfully !!");
                this.setVisible(false);
                new CustomerForm().setVisible(true);
            } else if (userName.matches(View.COACH_REGEX)) {
                setUserID(userName);
                JOptionPane.showMessageDialog(this, "Login sucessfully !!");
                this.setVisible(false);
                new FitnessForm().setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Login failed !!", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_loginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel forgotpassword;
    private javax.swing.JButton guest;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton login;
    private javax.swing.JButton register;
    private javax.swing.JCheckBox remember;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}

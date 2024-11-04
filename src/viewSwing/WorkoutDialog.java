package viewSwing;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class WorkoutDialog extends JDialog {

    public WorkoutDialog(Frame owner, List<String> workoutDetails) {
        super(owner, "Workout Details", true);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner);

        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Workout List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        JPanel workoutPanel = new JPanel();
        workoutPanel.setLayout(new BoxLayout(workoutPanel, BoxLayout.Y_AXIS));
        workoutPanel.setBackground(Color.WHITE);

        if (workoutDetails != null && !workoutDetails.isEmpty()) {
            for (String detail : workoutDetails) {
                JLabel workoutLabel = new JLabel(detail);
                workoutLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                workoutLabel.setForeground(Color.BLACK);
                workoutPanel.add(workoutLabel);
            }
        } else {
            JLabel noWorkoutLabel = new JLabel("No workout information available.");
            noWorkoutLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noWorkoutLabel.setForeground(Color.BLACK);
            workoutPanel.add(noWorkoutLabel);
        }

        add(new JScrollPane(workoutPanel), BorderLayout.CENTER);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }

    public void showDialog() {
        setVisible(true);
    }
}

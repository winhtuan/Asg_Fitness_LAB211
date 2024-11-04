package viewSwing;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import model.TimeRange;

public class GetTimeForCourseRegisterDialog extends JDialog {

    private List<TimeRange> availableTimes = new ArrayList<>();

    public GetTimeForCourseRegisterDialog(Frame parent, int frequency) {
        super(parent, "Enter Available Times", true);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        List<JTextField> startTimes = new ArrayList<>();
        List<JTextField> durations = new ArrayList<>();

        Font labelFont = new Font("Arial", Font.BOLD, 14);
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14);

        for (int i = 0; i < frequency; i++) {
            JLabel startLabel = new JLabel("Start Time for Day " + (i + 1) + " (HH:mm): ");
            startLabel.setFont(labelFont);
            gbc.gridx = 0;
            gbc.gridy = i * 2;
            gbc.anchor = GridBagConstraints.WEST;
            panel.add(startLabel, gbc);

            JTextField startTimeField = new JTextField(10);
            startTimeField.setFont(textFieldFont);
            gbc.gridx = 1;
            panel.add(startTimeField, gbc);
            startTimes.add(startTimeField);

            JLabel durationLabel = new JLabel("Duration (hours) for Day " + (i + 1) + ": ");
            durationLabel.setFont(labelFont);
            gbc.gridx = 0;
            gbc.gridy = i * 2 + 1;
            panel.add(durationLabel, gbc);

            JTextField durationField = new JTextField(10);
            durationField.setFont(textFieldFont);
            gbc.gridx = 1;
            panel.add(durationField, gbc);
            durations.add(durationField);
        }

        add(panel, BorderLayout.CENTER);

        JButton submitButton = new JButton("Submit");
        submitButton.setFont(labelFont);
        submitButton.setBackground(new Color(173, 216, 230));
        submitButton.setForeground(Color.BLACK);
        submitButton.setFocusPainted(false);
        submitButton.addActionListener(e -> {
            availableTimes.clear();
            boolean isValid = true;

            for (int i = 0; i < frequency; i++) {
                try {
                    LocalTime startTime = LocalTime.parse(startTimes.get(i).getText().trim());
                    double duration = Double.parseDouble(durations.get(i).getText().trim());
                    LocalTime endTime = startTime.plusMinutes((long) (duration * 60));

                    availableTimes.add(new TimeRange(startTime, endTime));
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Invalid input for day " + (i + 1) + ". Please enter valid time and duration.", "Error", JOptionPane.ERROR_MESSAGE);
                    isValid = false;
                    break;
                }
            }

            if (isValid) {
                setVisible(false);
            }
        });

        add(submitButton, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(parent);
        setVisible(true); 
    }

    public List<TimeRange> showDialog() {
        return availableTimes;
    }
}

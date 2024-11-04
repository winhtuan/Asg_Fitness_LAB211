package viewSwing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import util.Validation;

public class TimeInputDialog extends JDialog {
    private JTextField timeField;
    private JTextField durationField;
    private LocalTime startTimeResult;
    private double durationResult;

    public TimeInputDialog(Window owner) {
        super(owner, "Enter Time", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());

        JLabel timeLabel = new JLabel("Enter Start Time (HH:MM): ");
        timeField = new JTextField(5);
        JLabel durationLabel = new JLabel("Enter Duration (hours): ");
        durationField = new JTextField(5);

        JPanel inputPanel = new JPanel();
        inputPanel.add(timeLabel);
        inputPanel.add(timeField);
        inputPanel.add(durationLabel);
        inputPanel.add(durationField);
        add(inputPanel, BorderLayout.CENTER);

        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    startTimeResult = new Validation().convertStringToLocalTime(timeField.getText().trim());
                    durationResult = Double.parseDouble(durationField.getText().trim());
                    dispose();
                } catch (DateTimeParseException | NumberFormatException ex) {
                    JOptionPane.showMessageDialog(TimeInputDialog.this, "Invalid time format or duration. Use HH:MM for time and a number for duration.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> dispose());

        pack();
        setLocationRelativeTo(owner);
    }

    public LocalTime getStartTime() {
        return startTimeResult;
    }

    public double getDuration() {
        return durationResult;
    }
}

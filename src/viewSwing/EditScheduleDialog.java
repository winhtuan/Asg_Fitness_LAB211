package viewSwing;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Schedule;
import model.TimeRange;
import service.ScheduleService;
import util.Validation;

public class EditScheduleDialog extends JDialog {

    private Schedule schedule;
    private ScheduleService scheduleService;

    public EditScheduleDialog(Frame owner, Schedule schedule, ScheduleService scheduleService) {
        super(owner, true);
        this.schedule = schedule;
        this.scheduleService = scheduleService;
        setTitle("Edit Schedule");
        setSize(300, 200);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE);

        JButton editDateButton = new JButton("Edit Date");
        JButton editTimeButton = new JButton("Edit Time");

        editDateButton.setForeground(Color.BLACK);
        editTimeButton.setForeground(Color.BLACK);

        editDateButton.addActionListener(e -> editDate());
        editTimeButton.addActionListener(e -> editTime());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        buttonPanel.add(editDateButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(editTimeButton, gbc);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void editDate() {
        String targetDateStr = JOptionPane.showInputDialog(this, "Enter the target date (dd/MM/yyyy):");
        if (targetDateStr == null || targetDateStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No date entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate targetDate = new Validation().convertStringToLocalDate(targetDateStr);
        if (targetDate != null) {
            List<LocalDate> sessionDates = schedule.getSessionDate();
            if (sessionDates.contains(targetDate)) {
                DateInputDialog newDateDialog = new DateInputDialog(this);
                newDateDialog.setTitle("Enter the new date (dd/MM/yyyy)");
                newDateDialog.setVisible(true);
                LocalDate newDate = newDateDialog.getDate();
                if (newDate != null && !newDate.isBefore(LocalDate.now())) {
                    scheduleService.editDate(schedule.getScheduleID(), sessionDates, targetDate, newDate);
                    JOptionPane.showMessageDialog(this, "Date updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Start date cannot be in the past or invalid date.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "The target date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid target date entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editTime() {
        String targetDateStr = JOptionPane.showInputDialog(this, "Enter the target date (dd/MM/yyyy):");
        if (targetDateStr == null || targetDateStr.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No date entered.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        LocalDate targetDate = new Validation().convertStringToLocalDate(targetDateStr);
        if (targetDate != null) {
            List<LocalDate> sessionDates = schedule.getSessionDate();
            List<TimeRange> availableTimes = schedule.getAvailableTimes();
            if (sessionDates.contains(targetDate)) {
                TimeInputDialog timeInputDialog = new TimeInputDialog(this);
                timeInputDialog.setVisible(true);
                LocalTime newStartTime = timeInputDialog.getStartTime();
                double duration = timeInputDialog.getDuration();
                if (newStartTime != null) {
                    if (!targetDate.isBefore(LocalDate.now())) {
                        scheduleService.editTime(schedule.getScheduleID(), availableTimes, sessionDates, targetDate, newStartTime, duration);
                        JOptionPane.showMessageDialog(this, "Time updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, "Start date cannot be in the past.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "No start time or duration entered.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "The specified date is not in the session list.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Invalid target date entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}

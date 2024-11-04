package viewSwing;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import util.Validation;

public class DateInputDialog extends JDialog {
    private JTextField dateField;
    private LocalDate dateResult;
    private Validation input = new Validation();
    public DateInputDialog(Window owner) {
        super(owner, "Enter Date", ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Enter Date (DD/MM/YYYY): ");
        dateField = new JTextField(10);
        JPanel inputPanel = new JPanel();
        inputPanel.add(label);
        inputPanel.add(dateField);
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
                String dateText = dateField.getText().trim();
                try {
                    dateResult = input.convertStringToLocalDate(dateText);
                    dispose(); 
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(DateInputDialog.this, "Invalid date format. Use DD/MM/YYYY.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        cancelButton.addActionListener(e -> dispose());
        
        pack();
        setLocationRelativeTo(owner);
    }
    
    public LocalDate getDate() {
        return dateResult;
    }
}

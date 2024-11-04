package viewSwing;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class NutritionDialog extends JDialog {

    public NutritionDialog(Frame owner, List<String> nutList) {
        super(owner, "Nutrition Details", true);
        setSize(400, 300);
        setLayout(new BorderLayout());
        setLocationRelativeTo(owner); 

        getContentPane().setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Nutrition List", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLACK);
        add(titleLabel, BorderLayout.NORTH);

        JPanel nutritionPanel = new JPanel();
        nutritionPanel.setLayout(new BoxLayout(nutritionPanel, BoxLayout.Y_AXIS)); 
        nutritionPanel.setBackground(Color.WHITE); 

        if (nutList != null && !nutList.isEmpty()) {
            for (String nutritionDetail : nutList) {
                JLabel nutritionLabel = new JLabel(nutritionDetail);
                nutritionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                nutritionLabel.setForeground(Color.BLACK);
                nutritionPanel.add(nutritionLabel); 
            }
        } else {
            JLabel noNutritionLabel = new JLabel("No nutrition information available.");
            noNutritionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            noNutritionLabel.setForeground(Color.BLACK);
            nutritionPanel.add(noNutritionLabel);
        }

        add(new JScrollPane(nutritionPanel), BorderLayout.CENTER); 

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> dispose());
        add(closeButton, BorderLayout.SOUTH);
    }

    public void showDialog() {
        setVisible(true);
    }
}

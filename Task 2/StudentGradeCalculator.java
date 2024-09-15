import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentGradeCalculator extends JFrame {
    private JTextField[] subjectFields;
    private JLabel totalMarksLabel;
    private JLabel percentageLabel;
    private JLabel gradeLabel;
    private JButton calculateButton;
    private final int numSubjects = 5;

    public StudentGradeCalculator() {
        setTitle("Student Grade Calculator");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(numSubjects + 4, 2, 5, 5));

        JLabel[] subjectLabels = new JLabel[numSubjects];
        subjectFields = new JTextField[numSubjects];

        for (int i = 0; i < numSubjects; i++) {
            subjectLabels[i] = new JLabel("Marks for Subject " + (i + 1) + ": ");
            subjectFields[i] = new JTextField();
            panel.add(subjectLabels[i]);
            panel.add(subjectFields[i]);
        }

        calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        panel.add(new JLabel());
        panel.add(calculateButton);

        totalMarksLabel = new JLabel("Total Marks: ");
        percentageLabel = new JLabel("Average Percentage: ");
        gradeLabel = new JLabel("Grade: ");

        panel.add(totalMarksLabel);
        panel.add(new JLabel());
        panel.add(percentageLabel);
        panel.add(new JLabel());
        panel.add(gradeLabel);
        panel.add(new JLabel());

        add(panel);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int totalMarks = 0;
                for (int i = 0; i < numSubjects; i++) {
                    int marks = Integer.parseInt(subjectFields[i].getText());
                    if (marks < 0 || marks > 100) {
                        throw new NumberFormatException();
                    }
                    totalMarks += marks;
                }

                int averagePercentage = totalMarks / numSubjects;
                String grade = calculateGrade(averagePercentage);

                totalMarksLabel.setText("Total Marks: " + totalMarks);
                percentageLabel.setText("Average Percentage: " + averagePercentage + "%");
                gradeLabel.setText("Grade: " + grade);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter valid marks (0-100) in all fields.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        private String calculateGrade(int averagePercentage) {
            if (averagePercentage >= 90) {
                return "A";
            } else if (averagePercentage >= 80) {
                return "B";
            } else if (averagePercentage >= 70) {
                return "C";
            } else if (averagePercentage >= 60) {
                return "D";
            } else if (averagePercentage >= 50) {
                return "E";
            } else {
                return "F";
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentGradeCalculator());
    }
}

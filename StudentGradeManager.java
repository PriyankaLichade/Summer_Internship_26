import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    double marks;

    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentGradeManager extends JFrame implements ActionListener {

    // Components
    JLabel nameLabel, marksLabel;
    JTextField nameField, marksField;
    JButton addButton, reportButton;
    JTextArea outputArea;

    // ArrayList to store students
    ArrayList<Student> students = new ArrayList<>();

    // Constructor
    StudentGradeManager() {

        setTitle("Student Grade Management System");
        setSize(500, 500);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Labels
        nameLabel = new JLabel("Student Name:");
        marksLabel = new JLabel("Marks:");

        // Text fields
        nameField = new JTextField(20);
        marksField = new JTextField(10);

        // Buttons
        addButton = new JButton("Add Student");
        reportButton = new JButton("Generate Report");

        // Text area
        outputArea = new JTextArea(20, 40);
        outputArea.setEditable(false);

        // Add action listeners
        addButton.addActionListener(this);
        reportButton.addActionListener(this);

        // Add components
        add(nameLabel);
        add(nameField);

        add(marksLabel);
        add(marksField);

        add(addButton);
        add(reportButton);

        add(new JScrollPane(outputArea));

        setVisible(true);
    }

    // Button actions
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addButton) {

            try {
                String name = nameField.getText();
                double marks = Double.parseDouble(marksField.getText());

                students.add(new Student(name, marks));

                outputArea.append("Student Added: " + name + " - " + marks + "\n");

                nameField.setText("");
                marksField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Please enter valid data.");
            }
        }

        if (e.getSource() == reportButton) {

            if (students.size() == 0) {
                outputArea.setText("No student data available.");
                return;
            }

            double total = 0;
            double highest = students.get(0).marks;
            double lowest = students.get(0).marks;

            String highestStudent = students.get(0).name;
            String lowestStudent = students.get(0).name;

            StringBuilder report = new StringBuilder();

            report.append("====== Student Report ======\n\n");

            for (Student s : students) {

                report.append("Name: ")
                      .append(s.name)
                      .append(" | Marks: ")
                      .append(s.marks)
                      .append("\n");

                total += s.marks;

                if (s.marks > highest) {
                    highest = s.marks;
                    highestStudent = s.name;
                }

                if (s.marks < lowest) {
                    lowest = s.marks;
                    lowestStudent = s.name;
                }
            }

            double average = total / students.size();

            report.append("\n====== Summary ======\n");
            report.append("Average Marks: ").append(average).append("\n");
            report.append("Highest Marks: ").append(highest)
                  .append(" (").append(highestStudent).append(")\n");

            report.append("Lowest Marks: ").append(lowest)
                  .append(" (").append(lowestStudent).append(")\n");

            outputArea.setText(report.toString());
        }
    }

    // Main method
    public static void main(String[] args) {
        new StudentGradeManager();
    }
}
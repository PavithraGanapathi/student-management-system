package com.example.sms.ui;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;

import javax.swing.*;
import java.awt.*;

public class StudentFormPanel extends JPanel {
    private final JTextField idField = new JTextField(10);
    private final JTextField nameField = new JTextField(20);
    private final JTextField emailField = new JTextField(20);
    private final JTextField deptField = new JTextField(20);
    private final JButton addButton = new JButton("Add");
    private final JButton updateButton = new JButton("Update");
    private final JButton clearButton = new JButton("Clear");

    private final StudentService service = new StudentService();
    private final Runnable onSaved;

    public StudentFormPanel(Runnable onSaved) {
        this.onSaved = onSaved;
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // spacing between components
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;
        // Heading
        JLabel heading = new JLabel("Student Details");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        add(heading, gbc);
        y++;

        gbc.gridwidth = 1;
        // ID (editable for update)
        JLabel idLabel = new JLabel("Student ID:");
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = y; add(idLabel, gbc);
        gbc.gridx = 1; add(idField, gbc);
        y++;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = y; add(nameLabel, gbc);
        gbc.gridx = 1; add(nameField, gbc);
        y++;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = y; add(emailLabel, gbc);
        gbc.gridx = 1; add(emailField, gbc);
        y++;

        JLabel deptLabel = new JLabel("Department:");
        deptLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = y; add(deptLabel, gbc);
        gbc.gridx = 1; add(deptField, gbc);
        y++;

        // Buttons row
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setOpaque(false);
        addButton.setPreferredSize(new Dimension(90, 30));
        updateButton.setPreferredSize(new Dimension(90, 30));
        clearButton.setPreferredSize(new Dimension(90, 30));
        btnPanel.add(addButton);
        btnPanel.add(updateButton);
        btnPanel.add(clearButton);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        add(btnPanel, gbc);

        // Button actions
        addButton.addActionListener(e -> onAdd());
        updateButton.addActionListener(e -> onUpdate());
        clearButton.addActionListener(e -> clearForm());
    }

    private void onAdd() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dept = deptField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name is required");
            return;
        }
        Student s = new Student(name, email, dept);
        int r = service.addStudent(s);
        if (r > 0) {
            JOptionPane.showMessageDialog(this, "Added. ID=" + s.getStudentId());
            clearForm();
            onSaved.run();
        } else {
            JOptionPane.showMessageDialog(this, "Failed to add student");
        }
    }

    private void onUpdate() {
        String idText = idField.getText().trim();
        if (idText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter Student ID to update");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(idText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid ID");
            return;
        }
        Student existing = service.getStudent(id);
        if (existing == null) {
            JOptionPane.showMessageDialog(this, "Student not found");
            return;
        }
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String dept = deptField.getText().trim();
        if (!name.isEmpty()) existing.setName(name);
        if (!email.isEmpty()) existing.setEmail(email);
        if (!dept.isEmpty()) existing.setDepartment(dept);
        int r = service.updateStudent(existing);
        if (r > 0) {
            JOptionPane.showMessageDialog(this, "Updated");
            clearForm();
            onSaved.run();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed");
        }
    }

    private void clearForm() {
        idField.setText("");
        nameField.setText("");
        emailField.setText("");
        deptField.setText("");
    }
}

package com.example.sms.ui;

import com.example.sms.model.Student;
import com.example.sms.service.StudentService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class StudentTablePanel extends JPanel {
    private final StudentService service;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public StudentTablePanel(StudentService service) {
        this.service = service;
        setLayout(new BorderLayout());
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Email", "Department"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        JScrollPane sp = new JScrollPane(table);
        add(sp, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton refresh = new JButton("Refresh");
        JButton delete = new JButton("Delete");
        btns.add(refresh);
        btns.add(delete);
        add(btns, BorderLayout.SOUTH);

        refresh.addActionListener(e -> refreshTable());
        delete.addActionListener(e -> deleteSelected());

        refreshTable();
    }

    public void refreshTable() {
        List<Student> list = service.getAllStudents();
        tableModel.setRowCount(0);
        for (Student s : list) {
            tableModel.addRow(new Object[]{s.getStudentId(), s.getName(), s.getEmail(), s.getDepartment()});
        }
    }

    private void deleteSelected() {
        int r = table.getSelectedRow();
        if (r == -1) {
            JOptionPane.showMessageDialog(this, "Select a row to delete");
            return;
        }
        int id = (int) tableModel.getValueAt(r, 0);
        int ok = JOptionPane.showConfirmDialog(this, "Delete student ID="+id+"?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            int res = service.deleteStudent(id);
            if (res > 0) {
                JOptionPane.showMessageDialog(this, "Deleted");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Delete failed");
            }
        }
    }
}

package com.example.sms.ui;

import com.example.sms.service.StudentService;

import javax.swing.*;
import java.awt.*;

// A panel that paints a vertical sky-blue gradient background
class GradientPanel extends JPanel {
    private final Color top;
    private final Color bottom;

    public GradientPanel(Color top, Color bottom) {
        this.top = top;
        this.bottom = bottom;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, top, 0, h, bottom);
        g2.setPaint(gp);
        g2.fillRect(0, 0, w, h);
    }
}

public class MainFrame extends JFrame {
    private final StudentService service = new StudentService();
    private final StudentFormPanel formPanel;
    private final StudentTablePanel tablePanel;

    public MainFrame() {
        super("Student Management System - GUI");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        // Sky-blue gradient: top light sky, bottom near-white
        Color top = new Color(135, 206, 250);    // light sky blue
        Color bottom = new Color(240, 248, 255); // alice white-ish

        GradientPanel background = new GradientPanel(top, bottom);

        formPanel = new StudentFormPanel(this::onStudentAddedOrUpdated);
        tablePanel = new StudentTablePanel(service);

        // Make inner panels slightly transparent so gradient shows through
        formPanel.setOpaque(false);
        tablePanel.setOpaque(false);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tablePanel);
        split.setDividerLocation(360);
        split.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        split.setOpaque(false);

        background.add(split, BorderLayout.CENTER);
        setContentPane(background);
    }

    private void onStudentAddedOrUpdated() {
        // refresh table after add/update
        tablePanel.refreshTable();
    }
}

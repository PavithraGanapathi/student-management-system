package com.example.sms.dao;

import com.example.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarksDAO {
    public int addMarks(int studentId, int courseId, int marks) {
        String sql = "INSERT INTO marks (student_id, course_id, marks) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            pst.setInt(3, marks);
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public Double getAverageMarks(int studentId, int courseId) {
        String sql = "SELECT AVG(marks) as avgm FROM marks WHERE student_id=? AND course_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) return rs.getDouble("avgm");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

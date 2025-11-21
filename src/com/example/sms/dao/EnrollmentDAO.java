package com.example.sms.dao;

import com.example.sms.model.Course;
import com.example.sms.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentDAO {
    public int enroll(int studentId, int courseId) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int removeEnrollment(int studentId, int courseId) {
        String sql = "DELETE FROM enrollments WHERE student_id=? AND course_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            pst.setInt(2, courseId);
            return pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public List<Course> getEnrolledCourses(int studentId) {
        String sql = "SELECT c.course_id, c.course_name, c.duration_weeks FROM courses c " +
                "JOIN enrollments e ON c.course_id = e.course_id WHERE e.student_id = ?";
        List<Course> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, studentId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    list.add(new Course(
                            rs.getInt("course_id"),
                            rs.getString("course_name"),
                            rs.getInt("duration_weeks")
                        ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}

package com.example.sms.service;

import com.example.sms.dao.CourseDAO;
import com.example.sms.dao.EnrollmentDAO;
import com.example.sms.dao.MarksDAO;
import com.example.sms.dao.StudentDAO;
import com.example.sms.model.Course;
import com.example.sms.model.Student;

import java.util.List;

public class StudentService {
    private final StudentDAO studentDAO = new StudentDAO();
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    private final MarksDAO marksDAO = new MarksDAO();

    public int addStudent(Student s) {
        return studentDAO.save(s);
    }

    public int updateStudent(Student s) {
        return studentDAO.update(s);
    }

    public int deleteStudent(int id) {
        return studentDAO.delete(id);
    }

    public Student getStudent(int id) {
        return studentDAO.findById(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.findAll();
    }

    public List<Course> getAllCourses() {
        return courseDAO.findAll();
    }

    public int addCourse(Course c) {
        return courseDAO.save(c);
    }

    public int enrollStudent(int studentId, int courseId) {
        return enrollmentDAO.enroll(studentId, courseId);
    }

    public int removeEnrollment(int studentId, int courseId) {
        return enrollmentDAO.removeEnrollment(studentId, courseId);
    }

    public List<Course> getEnrolledCourses(int studentId) {
        return enrollmentDAO.getEnrolledCourses(studentId);
    }

    public int addMarks(int studentId, int courseId, int marks) {
        return marksDAO.addMarks(studentId, courseId, marks);
    }

    public Double getAverageMarks(int studentId, int courseId) {
        return marksDAO.getAverageMarks(studentId, courseId);
    }

    public String calculateGrade(Double avg) {
        if (avg == null) return "N/A";
        if (avg >= 90) return "A+";
        if (avg >= 80) return "A";
        if (avg >= 70) return "B";
        if (avg >= 60) return "C";
        if (avg >= 50) return "D";
        return "F";
    }
}

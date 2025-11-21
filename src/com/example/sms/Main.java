package com.example.sms;

import com.example.sms.model.Course;
import com.example.sms.model.Student;
import com.example.sms.service.StudentService;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final StudentService service = new StudentService();

    public static void main(String[] args) {
        System.out.println("=== Student Management System ===");
        boolean run = true;
        while (run) {
            menu();
            switch (scanner.nextLine().trim()) {
                case "1": addStudent(); break;
                case "2": updateStudent(); break;
                case "3": deleteStudent(); break;
                case "4": listStudents(); break;
                case "5": manageCourses(); break;
                case "6": enrollStudent(); break;
                case "7": addMarks(); break;
                case "8": showReport(); break;
                case "9": exportCSV(); break;
                case "0": run = false; break;
                default: System.out.println("Invalid option");
            }
        }
        System.out.println("Goodbye");
    }

    private static void menu() {
        System.out.println("\n1. Add Student\n2. Update Student\n3. Delete Student\n4. List Students\n5. Add/List Courses\n6. Enroll Student to Course\n7. Add Marks\n8. Show Performance Report\n9. Export Students to CSV\n0. Exit\nChoose:");
    }

    private static void addStudent() {
        System.out.print("Name: "); String name = scanner.nextLine();
        System.out.print("Email: "); String email = scanner.nextLine();
        System.out.print("Department: "); String dept = scanner.nextLine();
        Student s = new Student(name, email, dept);
        int r = service.addStudent(s);
        if (r>0) System.out.println("Added. ID="+s.getStudentId());
        else System.out.println("Failed to add");
    }

    private static void updateStudent() {
        System.out.print("Student ID to update: "); int id = Integer.parseInt(scanner.nextLine());
        Student s = service.getStudent(id);
        if (s==null) { System.out.println("Not found"); return; }
        System.out.print("New Name (enter to keep): "); String name = scanner.nextLine();
        if (!name.isEmpty()) s.setName(name);
        System.out.print("New Email (enter to keep): "); String email = scanner.nextLine();
        if (!email.isEmpty()) s.setEmail(email);
        System.out.print("New Department (enter to keep): "); String dept = scanner.nextLine();
        if (!dept.isEmpty()) s.setDepartment(dept);
        int r = service.updateStudent(s);
        System.out.println(r>0?"Updated":"Update failed");
    }

    private static void deleteStudent() {
        System.out.print("Student ID to delete: "); int id = Integer.parseInt(scanner.nextLine());
        int r = service.deleteStudent(id);
        System.out.println(r>0?"Deleted":"Delete failed or not found");
    }

    private static void listStudents() {
        List<Student> list = service.getAllStudents();
        list.forEach(System.out::println);
    }

    private static void manageCourses() {
        System.out.println("1. Add Course\n2. List Courses\nChoose:");
        String opt = scanner.nextLine();
        if (opt.equals("1")) {
            System.out.print("Course name: "); String name = scanner.nextLine();
            System.out.print("Duration (weeks): "); int d = Integer.parseInt(scanner.nextLine());
            Course c = new Course(name, d);
            int r = service.addCourse(c);
            System.out.println(r>0?"Course added":"Add failed");
        } else {
            List<Course> courses = service.getAllCourses();
            courses.forEach(System.out::println);
        }
    }

    private static void enrollStudent() {
        System.out.print("Student ID: "); int sid = Integer.parseInt(scanner.nextLine());
        System.out.print("Course ID: "); int cid = Integer.parseInt(scanner.nextLine());
        int r = service.enrollStudent(sid, cid);
        System.out.println(r>0?"Enrolled":"Enrollment failed");
    }

    private static void addMarks() {
        System.out.print("Student ID: "); int sid = Integer.parseInt(scanner.nextLine());
        System.out.print("Course ID: "); int cid = Integer.parseInt(scanner.nextLine());
        System.out.print("Marks (0-100): "); int m = Integer.parseInt(scanner.nextLine());
        int r = service.addMarks(sid, cid, m);
        System.out.println(r>0?"Marks added":"Failed to add marks");
    }

    private static void showReport() {
        System.out.print("Student ID: "); int sid = Integer.parseInt(scanner.nextLine());
        List<Course> courses = service.getEnrolledCourses(sid);
        if (courses.isEmpty()) { System.out.println("No courses enrolled"); return; }
        for (Course c : courses) {
            Double avg = service.getAverageMarks(sid, c.getCourseId());
            String grade = service.calculateGrade(avg);
            System.out.println(c.getCourseName()+" - Avg: "+ (avg==null?"N/A":String.format("%.2f", avg)) + " Grade: " + grade);
        }
    }

    private static void exportCSV() {
        List<Student> list = service.getAllStudents();
        System.out.print("CSV file name (e.g. students.csv): "); String fname = scanner.nextLine();
        try (PrintWriter pw = new PrintWriter(new FileWriter(fname))) {
            pw.println("student_id,name,email,department");
            for (Student s : list) {
                pw.printf("%d,%s,%s,%s\n", s.getStudentId(), s.getName(), s.getEmail(), s.getDepartment());
            }
            System.out.println("Exported to " + fname);
        } catch (Exception e) {
            System.out.println("Export failed: " + e.getMessage());
        }
    }
}

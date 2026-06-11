package main;

import model.Course;
import model.Student;
import service.TrackerService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final TrackerService service = new TrackerService();
    private static final Scanner scanner = new Scanner(System.in);
    private static int nextStudentId = 1;

    public static void main(String[] args) {
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Student Performance Tracker v1.0   ║");
        System.out.println("╚══════════════════════════════════════╝");

        while (true) {
            printMenu();
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleAddStudent();
                    break;
                case "2":
                    handleAddCourse();
                    break;
                case "3":
                    handleAssignGrade();
                    break;
                case "4":
                    handleShowGpa();
                    break;
                case "5":
                    handleShowTop3();
                    break;
                case "6":
                    handleCourseAnalytics();
                    break;
                case "7":
                    handleStudentsAboveGpa();
                    break;
                case "8":
                    handleListAll();
                    break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown option. Please enter a number from the menu.");
            }
            System.out.println();
        }
    }

    // ─────────────────────────────────────────────
    // Menu
    // ─────────────────────────────────────────────

    private static void printMenu() {
        System.out.println("──────────────────────────────────────");
        System.out.println(" 1. Add student");
        System.out.println(" 2. Add course");
        System.out.println(" 3. Assign grade");
        System.out.println(" 4. Show student GPA");
        System.out.println(" 5. Show top-3 students");
        System.out.println(" 6. Course analytics (average scores)");
        System.out.println(" 7. Students above GPA threshold");
        System.out.println(" 8. List all students / courses");
        System.out.println(" 0. Exit");
        System.out.print("Enter option: ");
    }

    // ─────────────────────────────────────────────
    // Handlers
    // ─────────────────────────────────────────────

    private static void handleAddStudent() {
        System.out.print("Student name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: name cannot be empty.");
            return;
        }

        System.out.print("Group (e.g. CS-101): ");
        String group = scanner.nextLine().trim();
        if (group.isEmpty()) {
            System.out.println("Error: group cannot be empty.");
            return;
        }

        Student student = new Student(nextStudentId++, name, group);
        service.addStudent(student);
    }

    private static void handleAddCourse() {
        System.out.print("Course code (e.g. INF-101): ");
        String code = scanner.nextLine().trim();
        if (code.isEmpty()) {
            System.out.println("Error: course code cannot be empty.");
            return;
        }

        System.out.print("Course name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Error: course name cannot be empty.");
            return;
        }

        service.addCourse(new Course(code, name));
    }

    private static void handleAssignGrade() {
        int studentId = readInt("Student ID: ");
        if (studentId < 0) return;

        System.out.print("Course code: ");
        String courseCode = scanner.nextLine().trim();

        int score = readInt("Score (0-100): ");
        if (score < 0) return;

        service.assignGrade(studentId, courseCode, score);
    }

    private static void handleShowGpa() {
        int studentId = readInt("Student ID: ");
        if (studentId < 0) return;

        double gpa = service.getStudentGpa(studentId);
        if (gpa >= 0) {
            Student s = service.findStudentById(studentId);
            System.out.printf("GPA of %s: %.2f%n", s.getName(), gpa);
        }
    }

    private static void handleShowTop3() {
        List<Student> top3 = service.getTop3Students();
        if (top3.isEmpty()) {
            System.out.println("No students registered yet.");
            return;
        }
        System.out.println("Top-3 students by GPA:");
        int rank = 1;
        for (Student s : top3) {
            double gpa = service.getStudentGpa(s.getId());
            System.out.printf("  %d. %s (group: %s) — GPA: %.2f%n",
                    rank++, s.getName(), s.getGroup(), gpa);
        }
    }

    private static void handleCourseAnalytics() {
        Map<String, Double> averages = service.getCourseAverages();
        if (averages.isEmpty()) {
            System.out.println("No courses registered yet.");
            return;
        }
        System.out.println("Course analytics (average scores):");
        for (Map.Entry<String, Double> entry : averages.entrySet()) {
            System.out.printf("  %-12s — avg: %.2f%n", entry.getKey(), entry.getValue());
        }
    }

    private static void handleStudentsAboveGpa() {
        double threshold = readDouble("GPA threshold (e.g. 75.0): ");
        if (threshold < 0) return;

        List<Student> result = service.getStudentsAboveGpa(threshold);
        if (result.isEmpty()) {
            System.out.printf("No students with GPA above %.2f.%n", threshold);
            return;
        }
        System.out.printf("Students with GPA above %.2f:%n", threshold);
        for (Student s : result) {
            double gpa = service.getStudentGpa(s.getId());
            System.out.printf("  [ID %d] %s (group: %s) — GPA: %.2f%n",
                    s.getId(), s.getName(), s.getGroup(), gpa);
        }
    }

    private static void handleListAll() {
        List<Student> students = service.getAllStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered.");
        } else {
            System.out.println("All students:");
            for (Student s : students) {
                System.out.println("  " + s);
            }
        }

        List<Course> courses = service.getAllCourses();
        if (courses.isEmpty()) {
            System.out.println("No courses registered.");
        } else {
            System.out.println("All courses:");
            for (Course c : courses) {
                System.out.println("  " + c);
            }
        }
    }

    // ─────────────────────────────────────────────
    // Input helpers with validation
    // ─────────────────────────────────────────────

    /**
     * Reads an integer from stdin. Returns -1 on invalid input.
     */
    private static int readInt(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: expected an integer, got '" + line + "'.");
            return -1;
        }
    }

    /**
     * Reads a double from stdin. Returns -1 on invalid input.
     */
    private static double readDouble(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: expected a number, got '" + line + "'.");
            return -1;
        }
    }
}

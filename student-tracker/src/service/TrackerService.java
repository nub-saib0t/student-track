package service;

import model.Course;
import model.Grade;
import model.Student;

import java.util.*;

public class TrackerService {

    // Task 1 data structures
    private final List<Student> studentList = new ArrayList<>();
    private final List<Course> courseList = new ArrayList<>();
    private final List<Grade> gradeList = new ArrayList<>();

    // HashMap for fast lookup by student ID
    private final HashMap<Integer, Student> studentMap = new HashMap<>();
    // HashMap for fast lookup by course code
    private final HashMap<String, Course> courseMap = new HashMap<>();

    // ─────────────────────────────────────────────
    // Task 2 — Core service methods
    // ─────────────────────────────────────────────

    /**
     * Adds a new student. Rejects duplicate IDs.
     */
    public boolean addStudent(Student student) {
        if (studentMap.containsKey(student.getId())) {
            System.out.println("Error: student with ID " + student.getId() + " already exists.");
            return false;
        }
        studentList.add(student);
        studentMap.put(student.getId(), student);
        System.out.println("Student added: " + student);
        return true;
    }

    /**
     * Adds a new course. Rejects duplicate course codes.
     */
    public boolean addCourse(Course course) {
        if (courseMap.containsKey(course.getCourseCode())) {
            System.out.println("Error: course with code '" + course.getCourseCode() + "' already exists.");
            return false;
        }
        courseList.add(course);
        courseMap.put(course.getCourseCode(), course);
        System.out.println("Course added: " + course);
        return true;
    }

    /**
     * Assigns a grade to a student for a course.
     * Score must be between 0 and 100 (inclusive).
     */
    public boolean assignGrade(int studentId, String courseCode, int score) {
        Student student = studentMap.get(studentId);
        if (student == null) {
            System.out.println("Error: student with ID " + studentId + " not found.");
            return false;
        }

        Course course = courseMap.get(courseCode);
        if (course == null) {
            System.out.println("Error: course with code '" + courseCode + "' not found.");
            return false;
        }

        if (score < 0 || score > 100) {
            System.out.println("Error: score must be between 0 and 100. Got: " + score);
            return false;
        }

        // Update existing grade if present, otherwise add new
        for (Grade g : gradeList) {
            if (g.getStudent().getId() == studentId
                    && g.getCourse().getCourseCode().equals(courseCode)) {
                g.setScore(score);
                System.out.println("Grade updated: " + g);
                return true;
            }
        }

        Grade grade = new Grade(student, course, score);
        gradeList.add(grade);
        System.out.println("Grade assigned: " + grade);
        return true;
    }

    /**
     * Returns the GPA (average score) for a student.
     * Returns -1 if student not found or has no grades.
     */
    public double getStudentGpa(int studentId) {
        Student student = studentMap.get(studentId);
        if (student == null) {
            System.out.println("Error: student with ID " + studentId + " not found.");
            return -1;
        }

        List<Grade> studentGrades = getGradesForStudent(studentId);
        if (studentGrades.isEmpty()) {
            System.out.println("Student " + student.getName() + " has no grades yet.");
            return 0;
        }

        double sum = 0;
        for (Grade g : studentGrades) {
            sum += g.getScore();
        }
        return sum / studentGrades.size();
    }

    // ─────────────────────────────────────────────
    // Task 3 — Analytics
    // ─────────────────────────────────────────────

    /**
     * Returns all students whose GPA is strictly above the given threshold.
     */
    public List<Student> getStudentsAboveGpa(double threshold) {
        List<Student> result = new ArrayList<>();
        for (Student s : studentList) {
            double gpa = getStudentGpa(s.getId());
            if (gpa > threshold) {
                result.add(s);
            }
        }
        return result;
    }

    /**
     * Returns the top-3 students with the highest GPA.
     */
    public List<Student> getTop3Students() {
        List<Student> sorted = new ArrayList<>(studentList);
        sorted.sort((a, b) -> {
            double gpaA = getStudentGpa(a.getId());
            double gpaB = getStudentGpa(b.getId());
            return Double.compare(gpaB, gpaA); // descending
        });

        List<Student> top3 = new ArrayList<>();
        for (int i = 0; i < Math.min(3, sorted.size()); i++) {
            top3.add(sorted.get(i));
        }
        return top3;
    }

    /**
     * Returns a map of courseCode -> average score across all students.
     */
    public Map<String, Double> getCourseAverages() {
        Map<String, Double> result = new LinkedHashMap<>();
        for (Course course : courseList) {
            List<Grade> courseGrades = getGradesForCourse(course.getCourseCode());
            if (courseGrades.isEmpty()) {
                result.put(course.getCourseCode(), 0.0);
            } else {
                double sum = 0;
                for (Grade g : courseGrades) {
                    sum += g.getScore();
                }
                result.put(course.getCourseCode(), sum / courseGrades.size());
            }
        }
        return result;
    }

    // ─────────────────────────────────────────────
    // Helper / utility methods
    // ─────────────────────────────────────────────

    private List<Grade> getGradesForStudent(int studentId) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : gradeList) {
            if (g.getStudent().getId() == studentId) {
                result.add(g);
            }
        }
        return result;
    }

    private List<Grade> getGradesForCourse(String courseCode) {
        List<Grade> result = new ArrayList<>();
        for (Grade g : gradeList) {
            if (g.getCourse().getCourseCode().equals(courseCode)) {
                result.add(g);
            }
        }
        return result;
    }

    public Student findStudentById(int studentId) {
        return studentMap.get(studentId);
    }

    public List<Student> getAllStudents() {
        return Collections.unmodifiableList(studentList);
    }

    public List<Course> getAllCourses() {
        return Collections.unmodifiableList(courseList);
    }
}

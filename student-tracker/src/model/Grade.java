package model;

public class Grade {
    private Student student;
    private Course course;
    private int score;

    public Grade(Student student, Course course, int score) {
        this.student = student;
        this.course = course;
        this.score = score;
    }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Course getCourse() { return course; }
    public void setCourse(Course course) { this.course = course; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    @Override
    public String toString() {
        return "Grade{student=" + student.getName()
                + ", course=" + course.getCourseCode()
                + ", score=" + score + "}";
    }
}

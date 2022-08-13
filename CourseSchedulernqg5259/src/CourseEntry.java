/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author nicol
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String courseDescription;
    private int seats;

    public CourseEntry(String semester, String courseCode, String courseDescription, int seats) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.courseDescription = courseDescription;
        this.seats = seats;
    }

    public String getSemester() {
        return semester;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public int getSeats() {
        return seats;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }
}

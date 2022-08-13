
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author nicol
 */

public class CourseQueries {
    private static Connection connection;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getAllCourseSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try{
            addCourse = connection.prepareStatement("insert into app.course values (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getCourseDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> courses = new ArrayList<CourseEntry>();
        CourseEntry course = new CourseEntry("", "", "", 0);
        try{
            getAllCourses = connection.prepareStatement("select * from app.course where semester = (?)");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next()){
                course = new CourseEntry("", "", "", 0);
                course.setSemester(resultSet.getString(1));
                course.setCourseCode(resultSet.getString(2));
                course.setCourseDescription(resultSet.getString(3));
                course.setSeats(resultSet.getInt(4));
                courses.add(course);
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<String>();
        try{
            getAllCourseCodes = connection.prepareStatement("select coursecode from app.course");
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next()){
                courses.add(resultSet.getString(1));
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static ArrayList<String> getAllCourseSeats(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList<String>();
        try{
            getAllCourseSeats = connection.prepareStatement("select seats from app.course");
            resultSet = getAllCourseSeats.executeQuery();
            
            while(resultSet.next()){
                courses.add(resultSet.getString(1));
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropCourse = connection.prepareStatement("delete from app.course where semester = (?) and coursecode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}

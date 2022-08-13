import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Timestamp;

/**
 *
 * @author nicol
 */
public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addScheduleEntry;
    private static PreparedStatement getScheduleByStudent;
    private static PreparedStatement getScheduledStudentCount;
    private static PreparedStatement getScheduledStudentsByCourse;
    private static PreparedStatement getWaitlistedStudentsByCourse;
    private static PreparedStatement dropStudentScheduleByCourse;
    private static PreparedStatement dropScheduleByCourse;
    private static PreparedStatement updateScheduleEntry;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            addScheduleEntry = connection.prepareStatement("insert into app.schedule values (?, ?, ?, ?, ?)");
            addScheduleEntry.setString(1, entry.getSemester());
            addScheduleEntry.setString(2, entry.getCourseCode());
            addScheduleEntry.setString(3, entry.getStudentID());
            addScheduleEntry.setString(4, entry.getStatus());
            addScheduleEntry.setTimestamp(5, entry.getTimestamp());
            addScheduleEntry.executeUpdate();
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        ArrayList<ScheduleEntry> entries = new ArrayList<ScheduleEntry>();
        ScheduleEntry entry = new ScheduleEntry("", "", "", "", new Timestamp(System.currentTimeMillis()));
        connection = DBConnection.getConnection();
        try{
            getScheduleByStudent = connection.prepareStatement("select * from app.schedule where studentID = (?) and semester = (?)");
            getScheduleByStudent.setString(1, studentID);
            getScheduleByStudent.setString(2, semester);
            resultSet = getScheduleByStudent.executeQuery();
            
            while(resultSet.next()){
                entry = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                entries.add(entry);
            }
            
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return entries;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        int x = 0;
        connection = DBConnection.getConnection();
        try{
            getScheduledStudentCount = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?)");
            getScheduledStudentCount.setString(1, currentSemester);
            getScheduledStudentCount.setString(2, courseCode);
            resultSet = getScheduledStudentCount.executeQuery();
            while(resultSet.next()){
                x+=1;
            }
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        return x;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode) {
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        ScheduleEntry schedule = new ScheduleEntry("", "", "", "", new Timestamp(System.currentTimeMillis()));
        connection = DBConnection.getConnection();
        try{
            getScheduledStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?) and status = (?)");
            getScheduledStudentsByCourse.setString(1, semester);
            getScheduledStudentsByCourse.setString(2, courseCode);
            getScheduledStudentsByCourse.setString(3, "S");
            resultSet = getScheduledStudentsByCourse.executeQuery();
            
            while(resultSet.next()){
                schedule = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                schedules.add(schedule);
            }
            
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        ArrayList<ScheduleEntry> schedules = new ArrayList<ScheduleEntry>();
        ScheduleEntry schedule = new ScheduleEntry("", "", "", "", new Timestamp(System.currentTimeMillis()));
        connection = DBConnection.getConnection();
        try{
            getWaitlistedStudentsByCourse = connection.prepareStatement("select * from app.schedule where semester = (?) and coursecode = (?) and status = (?) order by timestamp ASC");
            getWaitlistedStudentsByCourse.setString(1, semester);
            getWaitlistedStudentsByCourse.setString(2, courseCode);
            getWaitlistedStudentsByCourse.setString(3, "W");
            resultSet = getWaitlistedStudentsByCourse.executeQuery();
            
            while(resultSet.next()){
                schedule = new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5));
                schedules.add(schedule);
            }
            
        } catch(SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return schedules;
    }
    
    public static void dropStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        try{
            dropStudent = connection.prepareStatement("delete from app.schedule where semester = (?) and studentid = (?)");
            dropStudent.setString(1, semester);
            dropStudent.setString(2, studentID);
            dropStudent.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropStudentScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and studentid = (?) and coursecode = (?)");
            dropStudentScheduleByCourse.setString(1, semester);
            dropStudentScheduleByCourse.setString(2, studentID);
            dropStudentScheduleByCourse.setString(3, courseCode);
            dropStudentScheduleByCourse.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try{
            dropScheduleByCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and coursecode = (?)");
            dropScheduleByCourse.setString(1, semester);
            dropScheduleByCourse.setString(2, courseCode);
            dropScheduleByCourse.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(String semester, ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try{
            updateScheduleEntry = connection.prepareStatement("update app.schedule set status = (?) where semester = (?) and coursecode = (?) and studentID = (?)");
            updateScheduleEntry.setString(1, "S");
            updateScheduleEntry.setString(2, semester);
            updateScheduleEntry.setString(3, entry.getCourseCode());
            updateScheduleEntry.setString(4, entry.getStudentID());
            updateScheduleEntry.executeUpdate();
        } catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
    }
}   
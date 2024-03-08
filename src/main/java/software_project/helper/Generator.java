package software_project.helper;




import software_project.EventManagement.Event;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Generator {

    private Generator() {
        throw new IllegalStateException("Utility class");
    }

    public static User rsToUser(ResultSet rs) throws SQLException {
        User tmpUser = new User();
        tmpUser.setFirstName(rs.getString("First_Name"));
        tmpUser.setLastName(rs.getString("Last_Name"));
        tmpUser.setUsername(rs.getString("User_Name"));
        tmpUser.setPhoneNumber(rs.getString("Phone"));
        tmpUser.setEmail(rs.getString("Email"));
        tmpUser.setPassword(rs.getString("Password"));
        tmpUser.setImagePath(rs.getString("image"));
        tmpUser.setUserType(rs.getString("User_Type"));
        return tmpUser;
    }





    public static PreparedStatement userToPS(PreparedStatement preparedStmt, User user) throws SQLException {
        preparedStmt.setString(1, Arrays.toString(user.getFirstName().toCharArray()));
        preparedStmt.setString(2, user.getLastName());
        preparedStmt.setString(3, user.getUsername());
        preparedStmt.setString(4, user.getEmail());
        preparedStmt.setString(5, user.getPassword());
        preparedStmt.setString(6, user.getPhoneNumber());
        preparedStmt.setString(7, user.getUserType());
        preparedStmt.setString(8, user.getImagePath());
        return preparedStmt;
    }

    public static PreparedStatement eventStatementToPS(PreparedStatement preparedStmt, EventService es) throws SQLException {
        preparedStmt.setString(1, es.getTitle());
        preparedStmt.setString(2, es.getDetails());
        preparedStmt.setString(3, es.getEventCategory());
        preparedStmt.setString(4, es.getPrice());
        preparedStmt.setString(5, es.getPlace());
        preparedStmt.setString(6, es.getStartTime());
        preparedStmt.setString(7, es.getEndTime());
        preparedStmt.setString(8, es.getBookingTime());
        return preparedStmt;
    }
    public static PreparedStatement venueStatementToPS(PreparedStatement preparedStmt, Places p) throws SQLException {
        preparedStmt.setString(1, p.getName());
        preparedStmt.setString(2, p.getCapacity());
        preparedStmt.setString(3, p.getAmenities());

        return preparedStmt;
    }

    public static PreparedStatement eventBookingStatementToPS(PreparedStatement preparedStmt, Event e) throws SQLException {
        preparedStmt.setInt(1, e.getServiceId());
        preparedStmt.setString(2, e.getDate());
        preparedStmt.setString(3, e.getTime());
        preparedStmt.setString(4, e.getDescription());
        preparedStmt.setString(5, e.getAttendeeCount());
        return preparedStmt;
    }
    public static PreparedStatement guestListStatementToPS(PreparedStatement preparedStmt, Event e, String Guest) throws SQLException {
        preparedStmt.setInt(1, e.getId());
        preparedStmt.setString(2, Guest);
        return preparedStmt;
    }
    public static PreparedStatement imageStatementToPS(PreparedStatement preparedStmt, Event e, String Path) throws SQLException {
        preparedStmt.setInt(1, e.getId());
        preparedStmt.setString(2, Path);
        return preparedStmt;
    }

    public static int getTimeDifference(String time1, String time2) {
        String[] parts1 = time1.split(":");
        String[] parts2 = time2.split(":");

        int minutes1 = Integer.parseInt(parts1[0]) * 60 + Integer.parseInt(parts1[1]);
        int minutes2 = Integer.parseInt(parts2[0]) * 60 + Integer.parseInt(parts2[1]);

        return minutes1 - minutes2;
    }

    public static boolean hasTimeConflict(String startTime1, String endTime1, String startTime2, String endTime2) {
        int start1 = parseTimeToMinutes(startTime1);
        int end1 = parseTimeToMinutes(endTime1);
        int start2 = parseTimeToMinutes(startTime2);
        int end2 = parseTimeToMinutes(endTime2);

        return (start1 < end2 && end1 > start2);
    }

    private static int parseTimeToMinutes(String time) {
        String[] parts = time.split(":");
        return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
    }


    public static boolean isTimeInsideInterval(String timeStr, String startStr, String endStr) {
        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime start = LocalTime.parse(startStr, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime end = LocalTime.parse(endStr, DateTimeFormatter.ofPattern("HH:mm"));

        return !time.isBefore(start) && !time.isAfter(end);
    }
}

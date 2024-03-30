package softwareproject.helper;
import softwareproject.database.DBConnection;
import softwareproject.eventmanagement.Event;
import softwareproject.eventmanagement.EventService;
import softwareproject.eventmanagement.Places;
import softwareproject.UserManagement.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;
import java.util.logging.Logger;

import static java.lang.Math.abs;

public class Generator {

    public static final DBConnection conn = new DBConnection();
    private static final Logger logger = Logger.getLogger(Generator.class.getName());


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
        preparedStmt.setString(1, user.getFirstName());
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
        preparedStmt.setString(6, e.getBalance());

        return preparedStmt;
    }
    public static PreparedStatement guestListStatementToPS(PreparedStatement preparedStmt,  String guest , int id) throws SQLException {
        preparedStmt.setInt(1, id);
        preparedStmt.setString(2, guest);
        return preparedStmt;
    }
    public static PreparedStatement imageStatementToPS(PreparedStatement preparedStmt, String path , int id) throws SQLException {
        preparedStmt.setInt(1, id);
        preparedStmt.setString(2, path);
        return preparedStmt;
    }

    public static PreparedStatement vendorsStatementToPS(PreparedStatement preparedStmt,  String vendor , int id) throws SQLException {
        preparedStmt.setString(1, vendor);
        preparedStmt.setInt(2, id);
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
        final String HourMiunt="HH:mm";
        LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern(HourMiunt));
        LocalTime start = LocalTime.parse(startStr, DateTimeFormatter.ofPattern(HourMiunt));
        LocalTime end = LocalTime.parse(endStr, DateTimeFormatter.ofPattern(HourMiunt));

        return !time.isBefore(start) && !time.isAfter(end);
    }


    public static int starCounter(String rating) {
        int originalLength = rating.length();
        int withoutStarsLength = rating.replace("\\*", "").length();
        return originalLength - withoutStarsLength;
    }


    public static void printCalendar(int year, int month, Map<Integer, Boolean> colorMap) {
        if (month < 1 || month > 12) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        int startDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

        int numDaysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        System.out.println(" " + getMonthName(month) + " " + year);
        System.out.println(" Su Mo Tu We Th Fr Sa");

        for (int i = 1; i < startDayOfWeek; i++) {
            System.out.print("   ");
        }

        for (int i = 1; i <= numDaysInMonth; i++) {
            String colorCode = colorMap.containsKey(i) && Boolean.TRUE.equals(!colorMap.get(i)) ? "\u001B[31m" : "\u001B[34m"; // Red for false, Blue for true
            System.out.printf("%s%3d", colorCode, i);
            if ((startDayOfWeek + i - 1) % 7 == 0) {
                System.out.println();
            }
            System.out.print("\u001B[0m"); // Reset color
        }
        System.out.println("\n");
    }

    public static String generateDateString(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        return date.format(formatter);
    }

    public static String getMonthName(int month) {
        String[] monthNames = {"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};
        return monthNames[month-1];
    }


    public static void main(String[] args)
    {
        int c = (abs(getTimeDifference("14:00","00:00"))/60);
        logger.info(String.valueOf(c));

    }


}
package software_project.helper;



import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieve;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {
    private static String validUser = "Valid";
    private static String validEvent = "Valid";
    private static String validvenue = "Valid";

    private static retrieve r;

    private validation() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean regexMatcher(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private static String emptyUserFieldsTest(User user) {
        if (user.getEmail().isEmpty()) return "Email address can't be empty";
        if (user.getPhoneNumber().isEmpty()) return "Phone number can't be empty";
        if (user.getPassword().isEmpty()) return "Password can't be empty";
        if (user.getUsername().isEmpty()) return "Username can't be empty";
        if (user.getFirstName().isEmpty()) return "First name can't be empty";
        if (user.getLastName().isEmpty()) return "Last name can't be empty";
        if (user.getUserType().isEmpty()) return "User type can't be empty";
        return validUser;
    }

    private static String emptyEventServiceFieldsTest(EventService es) {
        if (es.getTitle().isEmpty()) return "You should enter a title for the event";
        else if (es.getDetails().isEmpty()) return "You should enter details for the event";
        else if (es.getEventCategory().isEmpty()) return "You should enter an event category for the event";
        else if (es.getPrice().isEmpty()) return "You should enter a price for the event";
        else if (es.getPlace().isEmpty()) return "You should enter a place for the event";
        else if (es.getStartTime().isEmpty()) return "You should enter a start time for the event";
        else if (es.getEndTime().isEmpty()) return "You should enter an end time for the event";
        else if (es.getBookingTime().isEmpty()) return "You should enter a booking time for the event";
        return validEvent;
    }
    private static String emptyVenueTest(Places place) {
        if (place.getName().isEmpty()) return "venue name can't be empty";
        if (place.getAmenities().isEmpty()) return "amenities can't be empty";
        if (place.getCapacity().isEmpty()) return "capacity can't be empty";

        return validvenue;
    }


    public static String rollValidationTest(User roll) {
        String emptyFieldsTest = emptyUserFieldsTest(roll);
        if (!emptyFieldsTest.equals(validUser))
            return emptyFieldsTest;
        if (emailValidationTest(roll.getEmail())) {
            if (phoneNumberValidationTest(roll.getPhoneNumber())) {
                if (passwordValidationTest(roll.getPassword())) {
                    if (roll.getUserType().equals("admin") || roll.getUserType().equals("customer") || roll.getUserType().equals("service provider"))
                        return validUser;
                    else {
                        return "Invalid user type";
                    }
                } else {
                    return "Invalid password";
                }
            }
            return "Invalid phone number";
        }
        return "Invalid email address";
    }

    public static String eventServiceValidationTest(EventService es) {
        String emptyFieldsTest = emptyEventServiceFieldsTest(es);

        r = new retrieve(new DB_Connection().getCon());

        if (!emptyFieldsTest.equals(validEvent))
            return emptyFieldsTest;
        else if (!integerValidationTest(es.getPrice())){
            return "Price should be integer value";
        }
        else if (!integerValidationTest(es.getBookingTime())){
            return "Booking time should be integer value";
        }
        else if (Generator.getTimeDifference(es.getEndTime(),es.getStartTime()) <= Integer.parseInt(es.getBookingTime())){
            return "End time should be after at least BookingTime hours from start time";
        }
        for(int i=0; i < r.selectEventServicesOfParticularPlace(es.getPlace()).size() ; i++){
            boolean b = Generator.hasTimeConflict(r.selectEventServicesOfParticularPlace(es.getPlace()).get(i).getStartTime(),r.selectEventServicesOfParticularPlace(es.getPlace()).get(i).getEndTime(),es.getStartTime(),es.getEndTime());
            if(b)
                return "Schedule conflicts between the time interval of the event service and the time interval of the other event services";
        }
        return validEvent;
    }
    public static String venueServiceValidationTest(Places place) {
        String emptyFieldsTest = emptyVenueTest(place);

      // r = new retrieve(new DB_Connection().getCon());

        if (!emptyFieldsTest.equals(validvenue))
            return emptyFieldsTest;

        else if (!integerValidationTest(place.getCapacity())){
            return "capacity should be an integer value";
        }

        return validvenue;
    }

    public static boolean phoneNumberValidationTest(String phoneNumber) {
        String regex = "^[0-9]{10}$";
        return regexMatcher(regex, phoneNumber);
    }

    public static boolean integerValidationTest(String number) {
        String regex = "^[0-9]{1,}$";
        return regexMatcher(regex, number);
    }



    public static boolean emailValidationTest(String email) {
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return regexMatcher(regex, email);
    }

    public static boolean passwordValidationTest(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        return regexMatcher(regex, password);

    }


}

package software_project.helper;



import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieve;
import software_project.EventManagement.Event;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.Vendor.AVendorBooking;
import software_project.Vendor.VendorService;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validation {
    private static String validUser = "Valid";
    private static String validEvent = "Valid";
    private static String validvenue = "Valid";
    private static String validVendorService = "Valid";

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
                    if (roll.getUserType().equals("admin") || roll.getUserType().equals("customer") || roll.getUserType().equals("service provider") || roll.getUserType().equals("vendor"))
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


        if (!emptyFieldsTest.equals(validvenue))
            return emptyFieldsTest;

        else if (!integerValidationTest(place.getCapacity())){
            return "capacity should be an integer value";
        }

        return validvenue;
    }


    public static String eventValidationTest(Event e) throws SQLException {
        String emptyFieldsTest = emptyEventFieldsTest(e);

        r = new retrieve(new DB_Connection().getCon());

        if (!emptyFieldsTest.equals(validEvent))
            return emptyFieldsTest;
        else if (!integerValidationTest(e.getAttendeeCount())) {
            return "Attendee count should be integer value";
        } else if (!integerValidationTest(e.getBalance())) {
            return "balance should be integer value";
        }

        EventService es = r.selectEventServicesOfParticularName(e.getServiceTitle());
        int total_vendors_price = 0;
        for (int i = 0; i < e.getVendors().size(); i++) {
            VendorService vs = r.selectVendorServiceOfParticularName(e.getVendors().get(i));
            total_vendors_price += (Integer.parseInt(vs.getServicePrice()) * Integer.parseInt(es.getBookingTime()));
        }
        if (Integer.parseInt(e.getBalance()) < (Integer.parseInt(es.getPrice()) + total_vendors_price)) {
            return "balance should be enough for vendors an event service";
        } else if (findNotFoundImages(e.getImages()).size() > 0) {
            String allNotFoundImages = findNotFoundImages(e.getImages()).get(0);
            for (int i = 1; findNotFoundImages(e.getImages()).size() > i; i++) {
                allNotFoundImages += "," + findNotFoundImages(e.getImages()).get(i);
            }
            return "Image " + allNotFoundImages + " not found";
        } else if (e.getGuestList().size() != Integer.parseInt(e.getAttendeeCount())) {
            return "Guest list members count should be equal to attendee count";
        }

        boolean b = Generator.isTimeInsideInterval(e.getTime(), es.getStartTime(), es.getEndTime());
        String[] parts = e.getTime().split(":");
        int part1 = Integer.parseInt(parts[0]) + Integer.parseInt(es.getBookingTime());

        String time = part1 + ":" + parts[1];

        boolean b2 = Generator.isTimeInsideInterval(time, es.getStartTime(), es.getEndTime());
        if (!b || !b2) {
            return "Time of the event should be in the interval of the chosen service";
        }

        for (Event ev : r.selectEventOfParticularDateAndServiceId(e.getDate(), e.getServiceId())) {
            String[] parts2 = ev.getTime().split(":");
            int part11 = Integer.parseInt(parts2[0]) + Integer.parseInt(es.getBookingTime());
            String time2 = part11 + ":" + parts2[1];

            boolean b3 = Generator.hasTimeConflict(e.getTime(), time, ev.getTime(), time2);
            if (b3)
                return "Schedule conflicts between the booking time of the event and the booking time of the other event from same service";
        }

        if (Integer.parseInt(e.getAttendeeCount()) > Integer.parseInt(r.retriveplace(es.getPlace()).getCapacity())) {
            return "Attendee count should be less than or equal the capacity of the place of the service";
        }

        for (String s : e.getGuestList()) {
            boolean containsOnlyAlphabetsAndSpaces = s.matches("[a-zA-Z ]+");

            if (containsOnlyAlphabetsAndSpaces) {
                continue;
            } else {
                return "Guest names must contain only alphabet letters";
            }
        }


        for (String s : e.getVendors()) {
            List<AVendorBooking> vbs = r.selectVendorBookingOfParticularName(s);
            if (!vbs.isEmpty()) {
                for (AVendorBooking vb : vbs) {
                    if (Objects.equals(vb.getBooking_date(), e.getDate())) {
                        String[] parts2 = vb.getStart_time().split(":");
                        int part11 = Integer.parseInt(parts2[0]) + Integer.parseInt(vb.getBooking_time());
                        String time2 = part11 + ":" + parts2[1];

                        boolean b3 = Generator.hasTimeConflict(e.getTime(), time, vb.getStart_time(), time2);
                        if (b3)
                        {

                            return "vendor " + vb.getVendor_user_name() + " is not available at this time";

                        }


                    }
                }
            }

        }
        return validEvent;
    }

    private static String emptyEventFieldsTest(Event e) {
        if (e.getServiceTitle().isEmpty()) return "You should choose a service";
        else if (e.getDate().isEmpty()) return "You should enter a date for the event";
        else if (e.getTime().isEmpty()) return "You should enter a time for the event";
        else if (e.getDescription().isEmpty()) return "You should enter a description for the event";
        else if (e.getBalance().isEmpty()) return "You should enter a balance for the event";
        else if (e.getAttendeeCount().isEmpty()) return "You should enter an attendee count for the event";
        else if (e.getGuestList().size()==1) return "You should enter a guest list for the event";
        return validEvent;
    }


    public static String vendorServiceValidationTest(VendorService vs) {
        String emptyFieldsTest = emptyVendorServiceFieldsTest(vs);

        r = new retrieve(new DB_Connection().getCon());

        if (!emptyFieldsTest.equals(validVendorService))
            return emptyFieldsTest;
        else if (!integerValidationTest(vs.getServicePrice())){
            return "ServicePrice must be numeric";
        }
        return validVendorService;
    }

    private static String emptyVendorServiceFieldsTest(VendorService vs) {
        if (vs.getServiceDescription().isEmpty()) return "ServiceDescription cannot be empty";
        else if (vs.getServicePrice().isEmpty()) return "ServicePrice cannot be empty";
        else if (vs.getServiceType().isEmpty()) return "ServiceType cannot be empty";
        return validVendorService;
    }
    public static List<String> findNotFoundImages(List<String> imagePaths) {
        List<String> notFoundImages = new ArrayList<>();

        for (String path : imagePaths) {
            File file = new File(path);
            if (!file.exists()) {
                notFoundImages.add(path);
            }
        }

        return notFoundImages;
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

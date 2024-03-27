package software_project.helper;



import software_project.DataBase.DBConnection;
import software_project.DataBase.retrieve.Retrieve;
import software_project.EventManagement.Event;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.Vendor.AVendorBooking;
import software_project.Vendor.VendorService;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    public static final String VALID = "Valid";
    private static final String VALID_USER = VALID;
    private static final String VALID_EVENT = VALID;
    private static final String VALID_VENUE = VALID;
    private static final String VALID_VENDOR_SERVICE = VALID;

    private static Retrieve r;

    static String vendorNotAvailable = "";

    private Validation() {
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
        return VALID_USER;
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
        return VALID_EVENT;
    }
    private static String emptyVenueTest(Places place) {
        if (place.getName().isEmpty()) return "venue name can't be empty";
        if (place.getAmenities().isEmpty()) return "amenities can't be empty";
        if (place.getCapacity().isEmpty()) return "capacity can't be empty";

        return VALID_VENUE;
    }


    public static String rollValidationTest(User roll) {
        String emptyFieldsTest = emptyUserFieldsTest(roll);
        if (!emptyFieldsTest.equals(VALID_USER))
            return emptyFieldsTest;
        if (emailValidationTest(roll.getEmail())) {
            if (phoneNumberValidationTest(roll.getPhoneNumber())) {
                if (passwordValidationTest(roll.getPassword())) {
                    if (roll.getUserType().equals("admin") || roll.getUserType().equals("customer") || roll.getUserType().equals("service provider") || roll.getUserType().equals("vendor"))
                        return VALID_USER;
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

        r = new Retrieve(new DBConnection().getCon());

        if (!emptyFieldsTest.equals(VALID_EVENT))
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
        return VALID_EVENT;
    }
    public static String venueServiceValidationTest(Places place) {
        String emptyFieldsTest = emptyVenueTest(place);


        if (!emptyFieldsTest.equals(VALID_VENUE))
            return emptyFieldsTest;

        else if (!integerValidationTest(place.getCapacity())){
            return "capacity should be an integer value";
        }

        return VALID_VENUE;
    }


    public static String eventValidationTest(Event e) throws SQLException {
        String emptyFieldsTest = emptyEventFieldsTest(e);
        r = new Retrieve(new DBConnection().getCon());

        if (!emptyFieldsTest.equals(VALID_EVENT))
            return emptyFieldsTest;

        if (!validateInteger(e.getAttendeeCount()))
            return "Attendee count should be integer value";

        if (!validateInteger(e.getBalance()))
            return "balance should be integer value";

        EventService es = r.selectEventServicesOfParticularName(e.getServiceTitle());
        int totalVendorsPrice = calculateTotalVendorsPrice(e, es);

        if (!balanceIsValid(e, es, totalVendorsPrice))
            return "balance should be enough for vendors an event service";


        List<String> notFoundImages = findNotFoundImages(e.getImages());
        if (!notFoundImages.isEmpty())
            return "Image " + String.join(",", notFoundImages) + " not found";

        if (!validateGuestListCount(e))
            return "Guest list members count should be equal to attendee count";

        if (!validateEventTime(e, es))
            return "Time of the event should be in the interval of the chosen service";

        if (!validateEventScheduleConflicts(e, es))
            return "Schedule conflicts between the booking time of the event and the booking time of the other event from same service";

        if (!validateAttendeeCount(es,e))
            return "Attendee count should be less than or equal the capacity of the place of the service";

        if (!validateGuestNames(e))
            return "Guest names must contain only alphabet letters";

        if (!validateVendorAvailability(e))
            return vendorNotAvailable;

        return VALID_EVENT;
    }

    private static boolean validateInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int calculateTotalVendorsPrice(Event e, EventService es) throws SQLException {
        int totalVendorsPrice = 0;
        for (String vendor : e.getVendors()) {
            VendorService vs = r.selectVendorServiceOfParticularName(vendor);
            totalVendorsPrice += Integer.parseInt(vs.getServicePrice()) * Integer.parseInt(es.getBookingTime());
        }
        return totalVendorsPrice;
    }

    private static boolean balanceIsValid(Event e, EventService es, int totalVendorsPrice) {
        return Integer.parseInt(e.getBalance()) >= (Integer.parseInt(es.getPrice()) + totalVendorsPrice);
    }


    private static boolean validateGuestListCount(Event e) {
        return e.getGuestList().size() == Integer.parseInt(e.getAttendeeCount());
    }

    private static boolean validateEventTime(Event e, EventService es) {
        boolean isEventTimeValid = Generator.isTimeInsideInterval(e.getTime(), es.getStartTime(), es.getEndTime());

        String[] parts = e.getTime().split(":");
        int part1 = Integer.parseInt(parts[0]) + Integer.parseInt(es.getBookingTime());
        String time = part1 + ":" + parts[1];

        boolean isAdjustedTimeValid = Generator.isTimeInsideInterval(time, es.getStartTime(), es.getEndTime());

        return isEventTimeValid && isAdjustedTimeValid;
    }


    private static boolean validateEventScheduleConflicts(Event e, EventService es) {
        for (Event ev : r.selectEventOfParticularDateAndServiceId(e.getDate(), e.getServiceId())) {
            String[] parts2 = ev.getTime().split(":");
            int part11 = Integer.parseInt(parts2[0]) + Integer.parseInt(es.getBookingTime());
            String time2 = part11 + ":" + parts2[1];

            boolean hasConflict = Generator.hasTimeConflict(e.getTime(), time2, ev.getTime(), time2);
            if (hasConflict)
                return false; // Conflict found, return false
        }
        return true; // No conflicts found
    }


    private static boolean validateAttendeeCount(EventService e , Event ee) throws SQLException {

        String placeCapacity = r.retriveplace(e.getPlace()).getCapacity();
        int attendeeCount = Integer.parseInt(ee.getAttendeeCount());
        int placeCapacityInt = Integer.parseInt(placeCapacity);

        return attendeeCount <= placeCapacityInt;
    }


    private static boolean validateGuestNames(Event e) {
        for (String guestName : e.getGuestList()) {
            boolean containsOnlyAlphabetsAndSpaces = guestName.matches("[a-zA-Z ]+");
            if (!containsOnlyAlphabetsAndSpaces) {
                return false; // Guest names must contain only alphabet letters
            }
        }
        return true; // All guest names are valid
    }


    private static boolean validateVendorAvailability(Event e) {
        for (String vendor : e.getVendors()) {
            List<AVendorBooking> vendorBookings = r.selectVendorBookingOfParticularName(vendor);

            for (AVendorBooking vb : vendorBookings) {
                if (Objects.equals(vb.getBookingdate(), e.getDate())) {
                    String[] parts2 = vb.getStarttime().split(":");
                    int part11 = Integer.parseInt(parts2[0]) + Integer.parseInt(vb.getBookingtime());
                    String time2 = part11 + ":" + parts2[1];

                    boolean hasConflict = Generator.hasTimeConflict(e.getTime(), time2, vb.getStarttime(), time2);
                    if (hasConflict) {
                        vendorNotAvailable = "vendor " + vb.getVendorusername() + " is not available at this time";
                        return false;
                    }
                }
            }
        }
        return true;
    }







    private static String emptyEventFieldsTest(Event e) {
        if (e.getServiceTitle().isEmpty()) return "You should choose a service";
        else if (e.getDate().isEmpty()) return "You should enter a date for the event";
        else if (e.getTime().isEmpty()) return "You should enter a time for the event";
        else if (e.getDescription().isEmpty()) return "You should enter a description for the event";
        else if (e.getBalance().isEmpty()) return "You should enter a balance for the event";
        else if (e.getAttendeeCount().isEmpty()) return "You should enter an attendee count for the event";
        else if (e.getGuestList().size()==1) return "You should enter a guest list for the event";
        return VALID_EVENT;
    }


    public static String vendorServiceValidationTest(VendorService vs) {
        String emptyFieldsTest = emptyVendorServiceFieldsTest(vs);

        r = new Retrieve(new DBConnection().getCon());

        if (!emptyFieldsTest.equals(VALID_VENDOR_SERVICE))
            return emptyFieldsTest;
        else if (!integerValidationTest(vs.getServicePrice())){
            return "ServicePrice must be numeric";
        }
        return VALID_VENDOR_SERVICE;
    }

    private static String emptyVendorServiceFieldsTest(VendorService vs) {
        if (vs.getServiceDescription().isEmpty()) return "ServiceDescription cannot be empty";
        else if (vs.getServicePrice().isEmpty()) return "ServicePrice cannot be empty";
        else if (vs.getServiceType().isEmpty()) return "ServiceType cannot be empty";
        return VALID_VENDOR_SERVICE;
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
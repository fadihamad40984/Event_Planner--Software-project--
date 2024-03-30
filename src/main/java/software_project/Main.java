package software_project;

import software_project.DataBase.DBConnection;
import software_project.DataBase.retrieve.Retrieve;
import software_project.EventManagement.Event;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.Vendor.AVendorBooking;
import software_project.Vendor.VendorService;
import software_project.authentication.Login;
import software_project.authentication.Register;
import software_project.helper.EmailSender;
import software_project.helper.Generator;
import software_project.helper.UserSession;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;
import java.util.logging.*;

import static java.lang.Math.abs;
import static java.lang.String.format;
import static java.lang.System.exit;


public class Main {
    private static final JFileChooser fileChooser = new JFileChooser();

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private static final Scanner scanner = new Scanner(System.in);
    private static final DBConnection conn = new DBConnection();
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    public static final String YOU_SHOULD_CHOOSE_NUMBER_ABOVE = "You should choose number above";
    public static final String YES = "1- Yes";
    public static final String ADMIN = "admin";
    public static final String DO_YOU_WANT_TO_CONTINUE_YES_NO = "Do you want to continue? (yes/no)";
    public static final String NUMBER = "Number";
    public static final String DESCRIPTION = "Description";
    public static final String DETAILS = "Details";
    public static final String ATTENDEE_COUNT = "Attendee_Count";
    public static final String EVENT_ID = "Event_id";
    public static final String SELECT_FROM_EVENT_WHERE_EVENT_ID = "select * from \"Event\" where \"Event_id\" = ";
    public static final String EVENT_SERVICE_ID = "EventService_id";
    public static final String BALANCE = "Balance";
    public static final String S_15_S_15_S_30_S_15_S_15_S_N = "%-15s%-15s%-15s%-30s%-15s%-15s%n";
    public static final String S_15_S_15_S_30_S_15_S_15_S_15_S_N = "%-15s%-15s%-15s%-30s%-15s%-15s%-15s%n";
    public static final String STATUS = "Status";
    public static final String NO = "2- No";
    public static final String INVALID_INPUT = "Invalid Input\n";
    public static final String EVENT_ID1 = "Event Id";
    public static final String S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N = "%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n";
    public static final String TITLE = "Title";
    public static final String EVENT_CATEGORY = "EventCategory";
    public static final String PRICE = "Price";
    public static final String PLACE = "Place";
    public static final String START_TIME = "StartTime";
    public static final String END_TIME = "EndTime";
    public static final String BOOKING_TIME = "BookingTime";
    public static final String TIME = "00:00";

     static int balanceBookEvent;



    static {

        System.setProperty("mail.debug", "false");


        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.OFF);
            }
        }
    }

    private static final Login login = new Login(conn.getCon());
    private static final Register register = new Register(conn.getCon());
    private static final EventManipulation eventManipulation = new EventManipulation(conn.getCon());

    private static final Retrieve retrieve = new Retrieve(conn.getCon());
    public static void main(String[] args) {





        try {
            logger.setUseParentHandlers(false);

            Handler[] handlers = logger.getHandlers();
            for (Handler handler : handlers) {
                logger.removeHandler(handler);
            }

            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.INFO);
            consoleHandler.setFormatter(new SimpleFormatter() {
                @Override
                public synchronized String format(LogRecord logRecord) {
                    return logRecord.getMessage() + "\n";
                }
            });
            logger.setUseParentHandlers(false);
            logger.addHandler(consoleHandler);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "An unexpected error occurred during logger configuration", e);
        }


        try {
            menu();

        }catch (Exception ii)
        {

            logger.info(ii.getMessage());
        }
    }



    public static void menu() throws IOException, SQLException {
        int ch;
        logger.info("***************************Login Page***************************\n");
        logger.info("""
                Choose Number\s
                1- Sign in
                2- Sign up""");

        ch = scanner.nextInt();

        if (ch==1)
            signinpage();
        else if(ch==2)
            signUpPage();
        else
            logger.severe(YOU_SHOULD_CHOOSE_NUMBER_ABOVE);

    }

    private static void signUpPage() throws IOException, SQLException {
        logger.info("***************************Register Page***************************\n");

        User user;

        String username;
        String firstName;
        String lastName;
        String phoneNumber;
        String password;
        String email;
        String imagePath;
        String userType;
        int code;

        while (true) {
            logger.info("Enter UserName : ");
            username = reader.readLine();
            logger.info("Enter FirstName : ");
            firstName = reader.readLine();
            logger.info("Enter LastName : ");
            lastName = reader.readLine();
            logger.info("Enter PhoneNumber : ");
            phoneNumber = reader.readLine();
            logger.info("Enter Password : ");
            password = reader.readLine();
            logger.info("Enter Email : ");
            email = reader.readLine();
            logger.info("Enter UserType : ");
            userType = reader.readLine();
            logger.info("Choose Image : ");
            imagePath = chooseImagePath();
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format("%n%s", imagePath));
            }




            EmailSender emailSender = new EmailSender(email);
            emailSender.sendVerificationCode();
            if(emailSender.isValidEmail())
            {
                logger.info("Enter Verification Code (Send To Your Email) : ");
                code = scanner.nextInt();

                if(code==emailSender.verificationCode)
                {
                    user=new User(username,firstName,lastName,phoneNumber,password,email,imagePath,userType);

                    register.registerUser(user);

                    if(Objects.equals(register.getStatus(), "User was registered successfully"))
                    {
                        signinpage();
                        return;
                    }
                    else {
                        logger.info(register.getStatus());

                    }
                }
                else
                {
                    logger.info("Invalid Verification Code");
                }

            }
            else
            {
                logger.info("Invalid Email");
            }







        }

    }

    private static void signinpage() throws IOException, SQLException {
        boolean continueLoop = true;

        while (continueLoop)
        {
            logger.info("Enter UserName : ");
            String username = reader.readLine();

            logger.info("Enter Password : ");

            String password = reader.readLine();

            boolean b =  login.loginUser(username, password);
            if(b)
            {
                logger.info("Login Successfully\n");
                if(Objects.equals(login.userType, ADMIN))
                {
                    adminpage();
                    return;

                }
                else if(Objects.equals(login.userType, "service provider"))
                {
                    serviceproviderpage();
                    return;

                }
                else if(Objects.equals(login.userType, "customer"))
                {
                    customerpage();
                    return;

                }

                else if(Objects.equals(login.userType, "vendor"))
                {
                    vendorpage();
                    return;

                }

            }
            else
            {
                logger.severe(login.getStatus());
                logger.info(DO_YOU_WANT_TO_CONTINUE_YES_NO);
                String userInput = reader.readLine();
                continueLoop = userInput.equals("yes");
            }
        }


    }

    private static void vendorpage() throws IOException, SQLException {
        logger.info("***************************Vendor Page***************************\n");
        List<Event> events;

        int choise;
        boolean continueloop = true;
        while(continueloop)
        {
            logger.info("1- Show Upcoming Events\n" +
                    "2- Log out");
            choise = scanner.nextInt();
            if(choise==1)
            {
                events = showUpcomingEventsForParticularVendor(UserSession.getCurrentUser().getUsername());

                if (logger.isLoggable(Level.INFO)){
                logger.info(format("%-15s%-15s%-15s%-30s%-15s%n",
                        NUMBER, "Date", "Time", DESCRIPTION, ATTENDEE_COUNT));}

                int counter = 0;
                for(Event e : events)
                {
                    if (logger.isLoggable(Level.INFO)){
                    logger.info(format("%-15s%-15s%-15s%-30s%-15s%n",
                            ++counter, e.getDate(), e.getTime(),
                            e.getDescription(), e.getAttendeeCount()));}
                }



            }
            else if(choise==2)
                menu();
            else
            {
                logger.info(DO_YOU_WANT_TO_CONTINUE_YES_NO);
                String userInput = reader.readLine();
                continueloop = userInput.equals("yes");
            }
        }
    }

    private static List<Event> showUpcomingEventsForParticularVendor(String username) throws SQLException , NullPointerException {
        List<Event> events = new ArrayList<>();
        List<Integer> eventsIDs = new ArrayList<>();

        try (PreparedStatement selectVendorBookingsStatement = conn.getCon().prepareStatement("SELECT * FROM \"Vendor_Bookings\" WHERE \"Vendor_UN\" = ?")) {
            selectVendorBookingsStatement.setString(1, username);
            try (ResultSet rs = selectVendorBookingsStatement.executeQuery()) {
                while (rs.next()) {
                    eventsIDs.add(rs.getInt(EVENT_ID));
                }

                for (int eventId : eventsIDs) {
                    try (PreparedStatement selectEventStatement = conn.getCon().prepareStatement(SELECT_FROM_EVENT_WHERE_EVENT_ID + "?")) {
                        selectEventStatement.setInt(1, eventId);
                        try (ResultSet rs1 = selectEventStatement.executeQuery()) {
                            while (rs1.next()) {
                                Event event = new Event(conn.getCon());
                                event.setId(rs1.getInt(EVENT_ID));
                                event.setDate(rs1.getString("Date"));
                                event.setDescription(rs1.getString(DESCRIPTION));
                                event.setTime(rs1.getString("Time"));
                                event.setAttendeeCount(rs1.getString(ATTENDEE_COUNT));
                                event.setServiceId(rs1.getInt(EVENT_SERVICE_ID));
                                event.setBalance(rs1.getString(BALANCE));
                                event.setUsername(username);
                                events.add(event);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return events;
    }


    private static void customerpage() throws IOException, SQLException {
        logger.info("***************************Customer Page***************************\n");
        int choise;
        boolean continueloop = true;
        while(continueloop)
        {
            logger.info("""
                    Choose Number\s
                    1- Book Event
                    2- Cancel Event
                    3- Check Request
                    4- Show Calendar
                    5- Log out""");
            choise = scanner.nextInt();
            if(choise==1)
                bookEventPage();
            else if(choise==2)
                cancelEventPage();
            else if(choise==3)
                checkRequestPage();
            else if(choise==4)
                showCalendarPage();
            else if(choise==5)
                menu();
            else
            {
                logger.info(DO_YOU_WANT_TO_CONTINUE_YES_NO);
                String userInput = reader.readLine();
                continueloop = userInput.equals("yes");
            }
        }



    }

    private static void showCalendarPage() throws SQLException, IOException {
        logger.info("Choose The Event You Want To Cancel :");
        List<Event> events;
        events = selectAllEventOfParticualrUserName(UserSession.getCurrentUser().getUsername());
        if (logger.isLoggable(Level.INFO)){
        logger.info(format(S_15_S_15_S_30_S_15_S_15_S_N,
                NUMBER, "Date", "Time", DESCRIPTION, ATTENDEE_COUNT, BALANCE));}

        int counter = 0;
        for(Event e : events)
        {
            if (logger.isLoggable(Level.INFO)){
            logger.info(format(S_15_S_15_S_30_S_15_S_15_S_N,
                    ++counter, e.getDate(), e.getTime(),
                    e.getDescription(), e.getAttendeeCount(), e.getBalance()));}
        }

        logger.info("Return To software_project.Main Page Enter \"ok\" ");
        String ch;
        ch= reader.readLine();
        if(Objects.equals(ch, "ok") || "OK".equals(ch))
        {
            customerpage();
        }




    }

    private static void checkRequestPage() throws SQLException, IOException {

        List<Event> events;
        List<String> status;
        status = selectStatusOfParticularUserName(UserSession.getCurrentUser().getUsername());
        events = selectAllRequestOfParticualrUserName(UserSession.getCurrentUser().getUsername());
        if (logger.isLoggable(Level.INFO)){
        logger.info(format(S_15_S_15_S_30_S_15_S_15_S_15_S_N,
                NUMBER, "Date", "Time", DESCRIPTION, ATTENDEE_COUNT, BALANCE, STATUS));}

        int counter = 0;
        for(Event e : events)
        {
            if (logger.isLoggable(Level.INFO)){
            logger.info(format(S_15_S_15_S_30_S_15_S_15_S_15_S_N,
                    ++counter, e.getDate(), e.getTime(),
                    e.getDescription(), e.getAttendeeCount(), e.getBalance() , status.get(counter-1)));}
        }

        logger.info("Do You Want To Return To software_project.Main Page : \s" +
                YES +
                NO);

        while (true)
        {
            int ch;
            ch = scanner.nextInt();
            if(ch==1)
            {
                customerpage();
                return;
            }
            else if(ch==2)
            {
                exit(0);
            }
            else{
                logger.info("Enter Valid Input\n");
            }

        }


    }

    private static void cancelEventPage() throws SQLException {

        logger.info("Choose The Event You Want To Cancel :");
        List<Event> events;
        events = selectAllEventOfParticualrUserName(UserSession.getCurrentUser().getUsername());

        if (logger.isLoggable(Level.INFO)){
        logger.info(format(S_15_S_15_S_30_S_15_S_15_S_N,
                NUMBER, "Date", "Time", DESCRIPTION, ATTENDEE_COUNT, BALANCE));}

        int counter = 0;
        for(Event e : events)
        {
            if (logger.isLoggable(Level.INFO)){
            logger.info(format(S_15_S_15_S_30_S_15_S_15_S_N,
                    ++counter, e.getDate(), e.getTime(),
                    e.getDescription(), e.getAttendeeCount(), e.getBalance()));}
        }

        int choise;

        while(true)
        {
            choise=scanner.nextInt();
            if(choise > 0 && choise <= counter)
            {

                sendRequest(events.get(choise-1));
                logger.info("Request Sent Successfully\n");
                break;
            }

            else{
                logger.severe(INVALID_INPUT);
                logger.info("Enter Another Choice\n");

            }
        }






    }

    private static void sendRequest(Event e) {


        try {
            conn.getCon().setAutoCommit(false);

            String query5 = "insert into \"Requests\"(\"UserName\",\"Event Id\" , \"Status\") values (?,?,?);";


            try (PreparedStatement preparedStmt5 = conn.getCon().prepareStatement(query5)) {
                preparedStmt5.setString(1, e.getUsername());
                preparedStmt5.setInt(2, e.getId());
                preparedStmt5.setString(3, "pending");
                preparedStmt5.execute();
            }
            conn.getCon().commit();
            conn.getCon().setAutoCommit(false);




            conn.getCon().commit();
        } catch (Exception exception) {
            logger.info(exception.getMessage());

        }





    }


    public static List<String> selectStatusOfParticularUserName(String username) throws SQLException {
        List<String> statuses = new ArrayList<>();
        try (PreparedStatement selectRequestsStatement = conn.getCon().prepareStatement("SELECT * FROM \"Requests\" WHERE \"UserName\" = ?")) {
            selectRequestsStatement.setString(1, username);
            try (ResultSet rs = selectRequestsStatement.executeQuery()) {
                while (rs.next()) {
                    statuses.add(rs.getString(STATUS));
                }
            }
        } catch (SQLException e) {
            logger.info(e.getMessage());
        }
        return statuses;
    }


    private static List<Event> selectAllRequestOfParticualrUserName(String username) throws SQLException {
        List<Event> events = new ArrayList<>();
        List<Integer> eventsIDs = new ArrayList<>();

        try (PreparedStatement selectRequestsStatement = conn.getCon().prepareStatement("SELECT * FROM \"Requests\" WHERE \"UserName\" = ?")) {
            selectRequestsStatement.setString(1, username);
            try (ResultSet rs = selectRequestsStatement.executeQuery()) {
                while (rs.next()) {
                    eventsIDs.add(rs.getInt(EVENT_ID1));
                }

                for (int eventId : eventsIDs) {
                    try (PreparedStatement selectEventStatement = conn.getCon().prepareStatement(SELECT_FROM_EVENT_WHERE_EVENT_ID + "?")) {
                        selectEventStatement.setInt(1, eventId);
                        try (ResultSet rs1 = selectEventStatement.executeQuery()) {
                            while (rs1.next()) {
                                Event event = new Event(conn.getCon());
                                event.setId(rs1.getInt(EVENT_ID));
                                event.setDate(rs1.getString("Date"));
                                event.setDescription(rs1.getString(DESCRIPTION));
                                event.setTime(rs1.getString("Time"));
                                event.setAttendeeCount(rs1.getString(ATTENDEE_COUNT));
                                event.setServiceId(rs1.getInt(EVENT_SERVICE_ID));
                                event.setBalance(rs1.getString(BALANCE));
                                event.setUsername(username);
                                events.add(event);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return events;
    }


    private static List<Event> selectAllEventOfParticualrUserName(String username) throws SQLException {
        List<Event> events = new ArrayList<>();
        List<Integer> eventsIDs = new ArrayList<>();

        try (PreparedStatement selectEventUserStatement = conn.getCon().prepareStatement("SELECT * FROM \"Event_User\" WHERE \"UserName\" = ?")) {
            selectEventUserStatement.setString(1, username);
            try (ResultSet rs = selectEventUserStatement.executeQuery()) {
                while (rs.next()) {
                    eventsIDs.add(rs.getInt(EVENT_ID1));
                }

                for (int eventId : eventsIDs) {
                    try (PreparedStatement selectEventStatement = conn.getCon().prepareStatement(SELECT_FROM_EVENT_WHERE_EVENT_ID + "?")) {
                        selectEventStatement.setInt(1, eventId);
                        try (ResultSet rs1 = selectEventStatement.executeQuery()) {
                            while (rs1.next()) {
                                Event event = new Event(conn.getCon());
                                event.setId(rs1.getInt(EVENT_ID));
                                event.setDate(rs1.getString("Date"));
                                event.setDescription(rs1.getString(DESCRIPTION));
                                event.setTime(rs1.getString("Time"));
                                event.setAttendeeCount(rs1.getString(ATTENDEE_COUNT));
                                event.setServiceId(rs1.getInt(EVENT_SERVICE_ID));
                                event.setBalance(rs1.getString(BALANCE));
                                event.setUsername(username);
                                events.add(event);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return events;
    }


    private static void bookEventPage() throws SQLException, IOException {
        List<EventService> allEvent = retrieve.retrieveAllEventServices();

        balanceBookEvent = getUserBalance();
        int storeBalance = balanceBookEvent;
        while (true) {
            int choice = displayEventServices(allEvent);
            if (choice != -1) {
                String date = getDateForBooking(allEvent, choice);
                EventService eventService = allEvent.get(choice - 1);
                List<Event> events = retrieveEvents(date, eventService);
                printEventsInParticularDate(events, eventService);
                List<String> times = calculateAvailableTimes(eventService, date);
                boolean b = displayAvailableTimes(times);
                if(!b){
                    continue;
                }
                String chosenTime = getTimeForBooking(allEvent, choice, date);
                balanceBookEvent -= Integer.parseInt(eventService.getPrice());
                String descr = getDescription();
                int attendeeCount = getAttendeeCount();
                List<String> guestList = getGuestList(attendeeCount);
                List<String> images = chooseImages();
                List<String> vendors = chooseVendors(date, chosenTime);
                Event accpetevent = new Event(conn.getCon());
                accpetevent.setDate(date);
                accpetevent.setDescription(descr);
                accpetevent.setTime(chosenTime);
                accpetevent.setAttendeeCount(String.valueOf(attendeeCount));
                accpetevent.setServiceTitle(allEvent.get(choice - 1).getTitle());
                accpetevent.setServiceId(allEvent.get(choice - 1).getId());
                accpetevent.setBalance(String.valueOf(storeBalance));
                accpetevent.setGuestList(guestList);
                accpetevent.setImages(images);
                accpetevent.setVendors(vendors);
                accpetevent.setUsername(UserSession.getCurrentUser().getUsername());
                eventManipulation.bookEvent(accpetevent);
                logger.severe(eventManipulation.getStatus());
                if (logger.isLoggable(Level.INFO)){
                logger.severe(String.format("Remaining Balance is: %s%n", balanceBookEvent));}
            }

            break;
        }
    }

    private static int getUserBalance() {
        logger.info("Enter The Balance : ");
        return scanner.nextInt();
    }

    private static int displayEventServices(List<EventService> allEvent) {
        int counter = 0;
        if (logger.isLoggable(Level.INFO)){
        logger.info(format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                NUMBER, TITLE, DETAILS, EVENT_CATEGORY, PRICE, PLACE, START_TIME, END_TIME, BOOKING_TIME));}
        for (EventService eventService : allEvent) {
            if (Integer.parseInt(eventService.getPrice()) <= balanceBookEvent) {
                if (logger.isLoggable(Level.INFO)){
                logger.info(format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                        ++counter, eventService.getTitle(), eventService.getDetails(),
                        eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                        eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));}
            } else {
                counter++;
            }
        }


        return getEventChoice(counter);
    }

    private static int getEventChoice(int counter) {
        while (true) {
            logger.info("Choose Event Service (By Enter A Number Of Service) : ");
            int choice = scanner.nextInt();
            if (choice > 0 && choice <= counter) {
                return choice;
            } else {
                logger.info("Enter A Valid Number\n");
            }
        }
    }

    private static String getDateForBooking(List<EventService> allEvent, int choice) {
        logger.info("Enter Year You Want To Book The Event : ");
        int year = scanner.nextInt();
        logger.info("Enter Month You Want To Book The Event : ");
        int month = scanner.nextInt();

        Generator.printCalendar(year, month, retrieve.checkDays(year, month, allEvent.get(choice - 1)));

        int day = 0;
        boolean validDay = false;
        while (!validDay) {
            logger.info("Enter Day You Want To Book The Event At: ");
            day = scanner.nextInt();

            if (day >= 1) {
                if ((month == 2 && ((year % 4 == 0 && day <= 29) || (day <= 28))) ||
                        ((month % 2 == 0 && day <= 30) || (month % 2 != 0 && day <= 31))) {
                    validDay = true; // Day is valid, exit loop
                } else {
                    logger.info("Invalid day for the selected month, please enter again.");
                }
            } else {
                logger.info("Day should be greater than or equal to 1.");
            }
        }

        return Generator.generateDateString(day, month, year);
    }

    private static List<Event> retrieveEvents(String date, EventService eventService) {
        return retrieve.selectEventOfParticularDateAndServiceId(date, eventService.getId());
    }

    private static void printEventsInParticularDate(List<Event> events, EventService eventService){
        if (events.isEmpty()) {
            logger.info("There Is No Events. You Can Request To Book An Event In This Day!");
        } else {
            if (logger.isLoggable(Level.INFO)){
            logger.info(format("%-15s%-15s%-15s%n",
                    START_TIME, END_TIME, DESCRIPTION));}
            for (Event e : events) {
                int c = (abs(Generator.getTimeDifference(e.getTime(), TIME)) / 60) + Integer.parseInt(eventService.getBookingTime());
                if (logger.isLoggable(Level.INFO)){
                logger.info(format("%-15s%-15s%-15s%n",

                        e.getTime(), c + ":00", e.getDescription()));}
            }
        }
    }

    private static List<String> calculateAvailableTimes(EventService eventService, String date) {
        List<String> times = new ArrayList<>();
        int timeDiff = abs(Generator.getTimeDifference(eventService.getStartTime(), eventService.getEndTime()));
        int bookingTime = Integer.parseInt(eventService.getBookingTime()) * 60;
        int numberOfEvents = timeDiff / bookingTime;
        String starttime = eventService.getStartTime();
        List<Event> allEvents = retrieveEvents(date, eventService);

        for (int i = 0; i < numberOfEvents; i++) {
            boolean flag = false;
            for (Event event2 : allEvents) {
                if (Objects.equals(starttime, event2.getTime())) {
                    starttime = ((abs(Generator.getTimeDifference(event2.getTime(), TIME)) / 60) + Integer.parseInt(eventService.getBookingTime())) + ":00";
                    flag = true;
                    break;
                }
            }
            if (flag) {
                continue;
            }
            times.add(starttime);
            starttime = ((abs(Generator.getTimeDifference(starttime, TIME)) / 60) + Integer.parseInt(eventService.getBookingTime())) + ":00";
        }

        return times;
    }

    private static boolean displayAvailableTimes(List<String> times) {
        if (times.isEmpty()) {
            logger.severe("Chosen Day Is Full");
            return false;
        } else {
            int count = 0;
            for (String time : times) {
                if (logger.isLoggable(Level.INFO)){
                logger.info(String.format("Time %d: %s", ++count, time));}
            }
        }
        return true;
    }

    private static String getTimeForBooking(List<EventService> allEvent, int choice, String date) {
        String chosenTime;
        List<String> times = calculateAvailableTimes(allEvent.get(choice-1), date);
        while (true) {
            logger.info("Choose The Time You Want To Book Event At : ");
            int chosenTimeIndex = scanner.nextInt();
            if (chosenTimeIndex > 0 && chosenTimeIndex <= times.size()) {
                chosenTime = times.get(chosenTimeIndex - 1);
                break;
            } else {
                logger.severe("Wrong option\n");
            }
        }
        return chosenTime;
    }

    private static String getDescription() throws IOException {
        logger.info("Enter Description : ");
        return reader.readLine();
    }

    private static int getAttendeeCount() {
        logger.info("Enter AttendeeCount : ");
        return scanner.nextInt();
    }

    private static List<String> getGuestList(int attendeeCount) throws IOException {
        List<String> guestList = new ArrayList<>();
        logger.info("Enter Guests Names : ");
        int k;
        for (k = 0; k < attendeeCount; k++) {
            String guest = reader.readLine();
            if (guest != null) {
                guestList.add(guest);
            } else {
                logger.severe("You Should Enter A name\n");
                k--;
            }
        }
        return guestList;
    }

    private static List<String> chooseImages() {
        List<String> images = new ArrayList<>();
        while (true) {
            String image;
            logger.info("Choose Image : ");
            image = chooseImagePath();
            images.add(image);
            logger.info("Do You Want Choose Another Image ?  " +
                    YES +" "+
                    NO);
            int ch = scanner.nextInt();
            if (ch != 1) {
                break;
            }
        }
        return images;
    }



    private static List<String> chooseVendors(String date, String chosenTime) throws SQLException {
        List<String> vendors;

            displayVendorServices(date, chosenTime);
            List<String> printedVendors = getPrintedVendors(date, chosenTime);
            if (printedVendors.isEmpty()) {
                logger.severe("No Vendor Matching With Remaining Balance");

            }
            vendors = selectVendors(printedVendors);


        return vendors;
    }

    private static void displayVendorServices( String date, String chosenTime) throws SQLException {
        if (logger.isLoggable(Level.INFO)){
        logger.info(format("%-15s%-25s%-40s%-15s%-15s%-20s%n",
                NUMBER, "Vendor_User_Name", DESCRIPTION, "Price/H", "Type", "Rating"));}
        int counterservice = 0;
        for (VendorService vs : allVendorServices()) {
            if (isVendorAvailable(vs, balanceBookEvent, date, chosenTime)) {
                displayVendorService(vs, counterservice);
                counterservice++;
            }
        }
    }

    private static List<String> getPrintedVendors(String date, String chosenTime) throws SQLException {
        List<String> printedVendors = new ArrayList<>();
        for (VendorService vs : allVendorServices()) {
            if (isVendorAvailable(vs, balanceBookEvent, date, chosenTime)) {
                printedVendors.add(vs.getVendorUserName());
            }
        }
        return printedVendors;
    }

    private static boolean isVendorAvailable(VendorService vs, int balance, String date, String chosenTime) throws SQLException {
        for (AVendorBooking vb : allNotAvailableVendors()) {
            if (vs.getVendorUserName().equals(vb.getVendorusername()) &&
                    date.equals(vb.getBookingdate()) &&
                    chosenTime.equals(vb.getStarttime())) {
                return false;
            }
        }
        return Integer.parseInt(vs.getServicePrice()) <= balance;
    }

    private static void displayVendorService(VendorService vs, int counterservice) {
        String description = vs.getServiceDescription().replace("\n", " ");
        StringBuilder rate = new StringBuilder();
        for (int i = 0; i < vs.getAverageRating(); i++) {
            rate.append("*");
        }
        if (logger.isLoggable(Level.INFO)){
        logger.info(format("%-15s%-25s%-40s%-15s%-15s%-20s%n",
                counterservice + 1, vs.getVendorUserName(), description,
                vs.getServicePrice(), vs.getServiceType(), rate));}
    }

    private static List<String> selectVendors( List<String> printedVendors) throws SQLException {
        List<String> vendors = new ArrayList<>();
        int counterService = printedVendors.size();
        boolean validInput = false;
        while (!validInput) {
            logger.info("Choose Vendor : ");
            int chooseVendor = scanner.nextInt();
            if (chooseVendor > 0 && chooseVendor <= counterService) {
                balanceBookEvent -= Integer.parseInt(allVendorServices().get(chooseVendor - 1).getServicePrice());
                vendors.add(printedVendors.get(chooseVendor - 1));
                logger.info("Do You Want Choose Another Vendor :  " +
                        YES +" "+
                        NO);
                int choiceInput = scanner.nextInt();
                if (choiceInput == 1) {
                    validInput = false;
                } else {
                    validInput = true;
                }
            } else {
                logger.severe(INVALID_INPUT);
            }
        }
        return vendors;
    }


    private static List<AVendorBooking> allNotAvailableVendors() throws SQLException {
        DBConnection conn = new DBConnection(5432,"Event_Planner","postgres", ADMIN);

        Statement stmt = null;

        List<AVendorBooking> vbs = new ArrayList<>();

        try {
            stmt = conn.getCon().createStatement();
            String query = "SELECT * FROM \"Vendor_NotAvailable\";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                AVendorBooking vb = new AVendorBooking();
                vb.setVendorusername(rs.getString("Vendor_UN"));
                vb.setBookingdate(rs.getString("Date"));
                vb.setStarttime(rs.getString("Time"));
                vbs.add(vb);


            }

        } catch (SQLException e) {
            logger.severe("Error DataBase");

        }finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return vbs;


    }



    private static List<VendorService> allVendorServices() throws SQLException {
        DBConnection conn = new DBConnection(5432,"Event_Planner","postgres", ADMIN);

        Statement stmt = null;

        List<VendorService> vss = new ArrayList<>();
        try {
            stmt = conn.getCon().createStatement();
            String query = "SELECT * FROM \"Vendor_Service\";";

            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                VendorService vs = new VendorService();

                vs.setVendorUserName(rs.getString("Vendor_User_Name"));
                vs.setServiceDescription(rs.getString(DESCRIPTION));
                vs.setServicePrice(rs.getString(PRICE));
                vs.setServiceType(rs.getString("Type"));
                vs.setAverageRating(Integer.parseInt(rs.getString("Average_Rating")));

                vss.add(vs);

            }

        } catch (SQLException e) {
            logger.severe("Error DataBase");

        }finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return vss;


    }

    private static void serviceproviderpage() throws IOException, SQLException {
        logger.info("***************************ServiceProvider Page***************************\n");

        boolean continueLoop = true;
        while (continueLoop) {
            int choice = displayServiceProviderOptions();

            switch (choice) {
                case 1 -> handleEventServiceManagement();
                case 2 -> handleVenueManagement();
                case 3 -> requestsPage();
                case 4 -> {
                    menu();
                    return;
                }
                default -> continueLoop = promptToContinue();
            }
        }
    }

    private static int displayServiceProviderOptions()  {
        logger.info("""
            1- Event Service Management
            2- Venue Management
            3- Check Requests
            4- Log out""");
        return scanner.nextInt();
    }

    private static void handleEventServiceManagement() throws IOException, SQLException {
        logger.info("***************************Event Service Management***************************\n");

        boolean continueLoop = true;
        while (continueLoop) {
            int choice = displayEventServiceManagementOptions();

            switch (choice) {
                case 1 -> addeventservice();
                case 2 -> editeventservice();
                case 3 -> deleteeventservice();
                case 4 -> {
                    menu();
                    return;
                }
                default -> {
                    logger.info(YOU_SHOULD_CHOOSE_NUMBER_ABOVE);
                    continueLoop = promptToContinue();
                }
            }
        }
    }

    private static int displayEventServiceManagementOptions()  {
        logger.info("1- Add EventService\s" +
                "2- Edit EventService" +
                "3- Delete EventService");
        return scanner.nextInt();
    }

    private static void handleVenueManagement() throws IOException, SQLException {
        logger.info("***************************Venue Management***************************\n");

        boolean continueLoop = true;
        while (continueLoop) {
            logger.info("1- Add Venue\n");
            int choice = scanner.nextInt();
            if (choice == 1) {
                addvenu();
                return;
            } else {
                logger.info(YOU_SHOULD_CHOOSE_NUMBER_ABOVE);
                continueLoop = promptToContinue();
            }
        }
    }

    private static boolean promptToContinue() throws IOException {
        logger.info(DO_YOU_WANT_TO_CONTINUE_YES_NO);
        String userInput = reader.readLine();
        return userInput.equals("yes");
    }
    private static void requestsPage() throws SQLException, IOException {
        logger.info("***************************Requests Page***************************\n");

        List<Event> events = selectAllRequests();
        List<String> status = selectAllStatus();


        if (logger.isLoggable(Level.INFO)){
            logger.info(format(S_15_S_15_S_30_S_15_S_15_S_15_S_N,
                    NUMBER, "Date", "Time", DESCRIPTION, ATTENDEE_COUNT, BALANCE, STATUS));}



        displayEvents(events, status);

        handleEventUpdates(events);

        returnToMainPage();
    }

    private static void displayEvents(List<Event> events, List<String> status) {
        int counter = 0;
        for (Event e : events) {
            if (logger.isLoggable(Level.INFO)){
            logger.info(format(S_15_S_15_S_30_S_15_S_15_S_15_S_N,
                    ++counter, e.getDate(), e.getTime(),
                    e.getDescription(), e.getAttendeeCount(), e.getBalance(), status.get(counter - 1)));}
        }
    }

    private static void handleEventUpdates(List<Event> events) {
        boolean continueLoop = true;

        while (continueLoop) {
            logger.info("Enter Event Number You Want To Accept/Refuse it : ");

            int eventNumber = scanner.nextInt();
            if (eventNumber > 0 && eventNumber <= events.size()) {
                logger.info("Status\s" +
                        "1- Accept" +
                        "2- Refuse");
                int statusChoice = scanner.nextInt();
                if (statusChoice == 1) {
                    updateAndDeleteEvent("accept", events.get(eventNumber - 1).getId(), events);
                } else if (statusChoice == 2) {
                    updateStatus("refuse", events.get(eventNumber - 1).getId());
                } else {
                    continueLoop = false; // Terminate loop
                }
            } else {
                logger.info(INVALID_INPUT);
            }
        }
    }

    private static void updateAndDeleteEvent(String status, int eventId, List<Event> events) {
        updateStatus(status, eventId);
        deleteEvent(eventId);
        events.removeIf(event -> event.getId() == eventId);
    }

    private static void returnToMainPage() throws SQLException, IOException {
        logger.info("Do You Want To Return To software_project.Main Page :  " +
                YES+"  " +
                NO);

        while (true) {
            int ch = scanner.nextInt();
            if (ch == 1) {
                serviceproviderpage();
                return;
            } else if (ch == 2) {
                exit(0);
            } else {
                logger.info("Enter Valid Input\n");
            }
        }
    }
    private static List<Event> selectAllRequests() throws SQLException, NullPointerException {
        List<Event> events = new ArrayList<>();
        List<String> users = new ArrayList<>();
        List<Integer> eventsIDs = new ArrayList<>();

        try (PreparedStatement selectRequestsStatement = conn.getCon().prepareStatement("SELECT * FROM \"Requests\";")) {
            try (ResultSet rs = selectRequestsStatement.executeQuery()) {
                while (rs.next()) {
                    eventsIDs.add(rs.getInt(EVENT_ID1));
                    users.add(rs.getString("UserName"));
                }

                for (int i = 0; i < eventsIDs.size(); i++) {
                    try (PreparedStatement selectEventStatement = conn.getCon().prepareStatement(SELECT_FROM_EVENT_WHERE_EVENT_ID + "?")) {
                        selectEventStatement.setInt(1, eventsIDs.get(i));
                        try (ResultSet rs1 = selectEventStatement.executeQuery()) {
                            while (rs1.next()) {
                                Event event = new Event(conn.getCon());
                                event.setId(rs1.getInt(EVENT_ID));
                                event.setDate(rs1.getString("Date"));
                                event.setDescription(rs1.getString(DESCRIPTION));
                                event.setTime(rs1.getString("Time"));
                                event.setAttendeeCount(rs1.getString(ATTENDEE_COUNT));
                                event.setServiceId(rs1.getInt(EVENT_SERVICE_ID));
                                event.setBalance(rs1.getString(BALANCE));
                                event.setUsername(users.get(i));
                                events.add(event);
                            }
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        return events;
    }


    private static List<String> selectAllStatus() throws SQLException {
        Statement stmt = null;
        List<String> statuses = new ArrayList<>();

        try {
            stmt = conn.getCon().createStatement();
            String query = "SELECT * FROM \"Requests\" ;";
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
            {
                statuses.add(rs.getString(STATUS));
            }
        }catch (Exception e){
            logger.info(e.getMessage());

        }finally {
            if (stmt != null) {
                stmt.close();
            }
        }

        return statuses;
    }


    public static boolean deleteEvent(int id) {

        try {
            conn.getCon().setAutoCommit(false);
            String query = "delete from \"Event\" where \"Event_id\" = ?;";
            try (PreparedStatement preparedStmt = conn.getCon().prepareStatement(query)) {
                preparedStmt.setInt(1, id);
                preparedStmt.execute();
            }
            conn.getCon().commit();
            return true;
        } catch (Exception e) {

            return false;
        }

    }

    public static boolean updateStatus(String status , int id) {
        try {
            conn.getCon().setAutoCommit(false);
            String query = "update \"Requests\" set \"Status\"= ? where \"Event Id\"=?";
            int rowsUpdated;
            try (PreparedStatement preparedStmt = conn.getCon().prepareStatement(query)) {
                preparedStmt.setString(1, status);
                preparedStmt.setInt(2, id);

                rowsUpdated = preparedStmt.executeUpdate();
            }
            if (rowsUpdated > 0) {
                conn.getCon().commit();
                return true;
            } else {
                conn.getCon().rollback();
                return false;
            }

        } catch (Exception e) {
            try {
                conn.getCon().rollback();
            } catch (SQLException rollbackException) {
                logger.info("Exception While inserting Data");
            }
            return false;
        }
    }






    private static void addvenu() throws IOException, SQLException {


        String name;
        String capacity;
        String amenities;
        Places place = new Places();

        logger.info("Enter Venue Name  : ");
        name = reader.readLine();
        logger.info("Enter Capacity  : ");
        capacity = reader.readLine();
        logger.info("Enter Amenities  : ");
        amenities = reader.readLine();

        place.setName(name);
        place.setCapacity(capacity);
        place.setAmenities(amenities);
        eventManipulation.addVenue(place);
        logger.info(eventManipulation.getStatus());
        serviceproviderpage();

    }

    private static void deleteeventservice() throws SQLException, IOException {
        List<EventService> allEvent = retrieve.retrieveAllEventServices();

        if (logger.isLoggable(Level.INFO)) {

            logger.info(format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                    "Id", TITLE, DETAILS, EVENT_CATEGORY, PRICE, PLACE, START_TIME, END_TIME, BOOKING_TIME));
        }
        for (EventService eventService : allEvent) {
            if (logger.isLoggable(Level.INFO)) {
                logger.info(format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                        eventService.getId(), eventService.getTitle(), eventService.getDetails(),
                        eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                        eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));
            }
        }

        int eventId;
        logger.info("Enter The Event_Id you want To Delete : ");
        eventId = scanner.nextInt();
        EventService es = new EventService();
        es.setId(eventId);
        eventManipulation.deleteEventService(es);
        if(Objects.equals(eventManipulation.getStatus(), "Event service deleted successfully"))
        {
            logger.info(eventManipulation.getStatus());
            serviceproviderpage();

        }

    }

    private static void editeventservice() throws IOException, SQLException {
        EventService es;

        String title;
        String details;
        String eventCategory;
        String price;
        String place;
        String startTime;
        String endTime;
        String bookingTime;



        List<EventService> allEvent = retrieve.retrieveAllEventServices();

        if (logger.isLoggable(Level.INFO)){
        logger.log(Level.INFO, String.format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                "Id", TITLE, DETAILS, EVENT_CATEGORY, PRICE, PLACE, START_TIME, END_TIME, BOOKING_TIME));
        }

        for (EventService eventService : allEvent) {
            if (logger.isLoggable(Level.INFO)) {
                logger.log(Level.INFO, String.format(S_20_S_15_S_15_S_15_S_15_S_15_S_15_S_15_S_N,
                        eventService.getId(), eventService.getTitle(), eventService.getDetails(),
                        eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                        eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));

            }
        }

        int id;
        logger.info("Enter Event Id Which you want to Edit : ");
        id = scanner.nextInt();
        logger.info("Enter Title : ");
        title = reader.readLine();
        logger.info("Enter Details : ");
        details = reader.readLine();
        logger.info("Enter EventCategory : ");
        eventCategory = reader.readLine();
        logger.info("Enter Price : ");
        price = reader.readLine();
        logger.info("Enter Place : ");
        place = reader.readLine();
        logger.info("Enter StartTime : ");
        startTime = reader.readLine();
        logger.info("Enter EndTime : ");
        endTime = reader.readLine();
        logger.info("Enter BookingTime : ");
        bookingTime = reader.readLine();


        es=new EventService(title,details,eventCategory,price,place,startTime,endTime,bookingTime);
        es.setId(id);

        eventManipulation.editEventService(es);
        logger.info(eventManipulation.getStatus());

        serviceproviderpage();



    }

    private static void addeventservice() throws IOException, SQLException {
        boolean continueLoop = true;
        EventService eventService;

        String title;
        String details;
        String eventCategory;
        String price;
        String place;
        String startTime;
        String endTime;
        String bookingTime;

        while (continueLoop) {
            logger.info("Enter Title : ");
            title = reader.readLine();
            logger.info("Enter Details : ");
            details = reader.readLine();
            logger.info("Enter EventCategory : ");
            eventCategory = reader.readLine();
            logger.info("Enter Price : ");
            price = reader.readLine();
            logger.info("Enter Place : ");
            place = reader.readLine();
            logger.info("Enter StartTime : ");
            startTime = reader.readLine();
            logger.info("Enter EndTime : ");
            endTime = reader.readLine();
            logger.info("Enter BookingTime : ");
            bookingTime = reader.readLine();

            eventService=new EventService(title,details,eventCategory,price,place,startTime,endTime,bookingTime);


            eventManipulation.addEventService(eventService);
            if(Objects.equals(eventManipulation.getStatus(), "Event added successfully"))
            {
                logger.info(eventManipulation.getStatus());
                serviceproviderpage();
                return;
            }
            else {
                logger.info(eventManipulation.getStatus());
                logger.info(DO_YOU_WANT_TO_CONTINUE_YES_NO);
                String userInput = reader.readLine();
                continueLoop = userInput.equals("yes");

            }



        }
        exit(0);

    }

    private static void adminpage() {

        logger.info("""
                Choose Number
                1- All Users Report\s
                2- All Events Report
                3- All EventService Report
                4- Delete User""");



    }


















































    public static String chooseImagePath() {
        fileChooser.setDialogTitle("Select Image");
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                }
                String extension = getExtension(file);
                return extension != null && isSupportedExtension(extension);
            }

            @Override
            public String getDescription() {
                return "Image files (*.png, *.jpg, *.jpeg, *.gif, *.bmp)";
            }
        });

        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        } else {
            return null;
        }
    }

    private static String getExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    private static boolean isSupportedExtension(String extension) {
        return extension.equals("png") ||
                extension.equals("jpg") ||
                extension.equals("jpeg") ||
                extension.equals("gif") ||
                extension.equals("bmp");
    }
}
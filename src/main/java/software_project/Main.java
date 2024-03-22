package software_project;


import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieve;
import software_project.EventManagement.Event;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;
import software_project.EventManagement.Places;
import software_project.UserManagement.User;
import software_project.authentication.Login;
import software_project.authentication.Register;
import software_project.helper.EmailSender;
import software_project.helper.Generator;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.*;

import static java.lang.Math.abs;
import static java.lang.Math.log;
import static java.lang.System.exit;

public class Main {
private static final JFileChooser fileChooser = new JFileChooser();

    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

   private static final Scanner scanner = new Scanner(System.in);
    private static final DB_Connection conn = new DB_Connection();
    private static final Logger logger = Logger.getLogger(Main.class.getName());
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

     private static final retrieve retrieve = new retrieve(conn.getCon());
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
                public synchronized String format(java.util.logging.LogRecord logRecord) {
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

        }catch (Exception ignored)
        {

        }
    }



    public static void menu() throws IOException, SQLException {
        int ch;
        logger.info("***************************Login Page***************************\n");
        logger.info("Choose Number \n" +
                "1- Sign in\n" +
                "2- Sign up");

        ch = scanner.nextInt();

        if (ch==1)
            signinpage();
        else if(ch==2)
            signuppage();
        else
            logger.severe("You should choose number above");

    }

    private static void signuppage() throws IOException, SQLException {
        logger.info("***************************Register Page***************************\n");

        boolean continueLoop = true;
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

        while (continueLoop) {
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
            logger.info("\n"+imagePath);



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
                if(Objects.equals(login.user_type, "admin"))
                {
                    adminpage();
                    return;

                }
                else if(Objects.equals(login.user_type, "service provider"))
            {
                serviceproviderpage();
                return;

            }
                else if(Objects.equals(login.user_type, "customer"))
            {
                customerpage();
                return;

            }

                else if(Objects.equals(login.user_type, "vendor"))
                {
                    vendorpage();
                    return;

                }

            }
            else
            {
                logger.severe(login.getStatus());
                logger.info("Do you want to continue? (yes/no)");
                String userInput = reader.readLine();
                continueLoop = userInput.equals("yes");
            }
        }


    }

    private static void vendorpage() {
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
                    2- Cancel Event""");
            choise = scanner.nextInt();
            if(choise==1)
                BookEventPage();
            else if(choise==2)
                CancelEventPage();
            else
            {
                logger.info("Do you want to continue? (yes/no)");
                String userInput = reader.readLine();
                continueloop = userInput.equals("yes");
            }
        }



    }

    private static void CancelEventPage() {
    }

    private static void BookEventPage() throws SQLException, IOException {
        List<EventService> AllEvent = retrieve.retrieveAllEventServices();

        int counter = 0;
        int choice;
        int year;
        int month;
        int day;
        String date;
        String time;
        String ChosenTime;
        String Description;
        int Balance;
        int StoreBalance;
        Event event = new Event(conn.getCon());
        List<String> Times = new ArrayList<>();


        logger.info("Enter The Balance : ");
        Balance = reader.read();
        StoreBalance = Balance;


        logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                "Number", "Title", "Details", "EventCategory", "Price", "Place", "StartTime", "EndTime", "BookingTime"));
        for (EventService eventService : AllEvent) {
            if(Integer.parseInt(eventService.getPrice()) <= Balance) {


                logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                        ++counter, eventService.getTitle(), eventService.getDetails(),
                        eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                        eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));
            }
        }




        while(true)
        {
            logger.info("Choose Event Service (By Enter A Number Of Service) : ");
            choice = scanner.nextInt();

            if(choice>0 && choice<=counter)
            {

                logger.info("Enter Year You Want To Book The Event : ");
                year = scanner.nextInt();
                logger.info("Enter Month You Want To Book The Event : ");
                month = scanner.nextInt();



                Generator.printCalendar(year,month,retrieve.CheckDays(year,month,AllEvent.get(choice-1)));


                while(true) {
                    while (true) {
                        logger.info("Enter Day You Want To Book The Event At: ");
                        day = scanner.nextInt();
                        if (day >= 1) {
                            if (month == 2) {

                                if (year % 4 == 0) {
                                    if (day > 29) {
                                        continue;
                                    }

                                    break;

                                } else {
                                    if (day > 28) {
                                        continue;
                                    }
                                    break;

                                }
                            } else if (month % 2 == 0) {
                                if (day > 30) {
                                    continue;
                                }
                                break;
                            } else if (month % 2 != 0) {
                                if (day > 31) {
                                    continue;
                                }
                                break;
                            } else {
                                break;
                            }


                        } else if (day < 1) {
                            continue;
                        }

                    }

                    date = Generator.generateDateString(day, month, year);

                    List<Event> events = retrieve.selectEventOfParticularDateAndServiceId(date, AllEvent.get(choice - 1).getId());

                    if (events.isEmpty()) {
                        logger.info("There Is No Events. You Can Request To Book An Event In This Day!");
                    } else {
                        logger.info(String.format("%-15s%-15s%-15s%n",
                                "StartTime", "EndTime", "Description"));
                        for (Event e : events) {
                            int c = (abs(Generator.getTimeDifference(e.getTime(), "00:00")) / 60) + Integer.parseInt(AllEvent.get(choice - 1).getBookingTime());
                            logger.info(String.format("%-15s%-15s%-15s%n",

                                    e.getTime(), c + ":00", e.getDescription()));
                        }
                    }



                    int TimeDiff = abs(Generator.getTimeDifference(AllEvent.get(choice - 1).getStartTime(), AllEvent.get(choice - 1).getEndTime()));
                    int BookingTime = Integer.parseInt(AllEvent.get(choice - 1).getBookingTime()) * 60;
                    int NumberOfEvents = TimeDiff / BookingTime;

                    int count = 0;

                    boolean flag = false;
                    boolean f = false;
                    String starttime = AllEvent.get(choice - 1).getStartTime();
                    List<Event> ALLEven = retrieve.selectEventOfParticularDateAndServiceId(date,AllEvent.get(choice - 1).getId());




                    for (int i = 0; i < NumberOfEvents; i++) {
                        for (Event event2 : ALLEven) {
                            if (Objects.equals(starttime, event2.getTime())) {
                                starttime = ((abs(Generator.getTimeDifference(event2.getTime(), "00:00")) / 60) + Integer.parseInt(AllEvent.get(choice - 1).getBookingTime())) + ":00";
                                flag = true;
                                break;

                            }


                        }
                        if (flag) {
                            continue;
                        }



                        Times.add(starttime);
                        logger.info("Time " + (++count) + ": " + starttime);
                        f = true;
                        starttime = ((abs(Generator.getTimeDifference(starttime, "00:00")) / 60) + Integer.parseInt(AllEvent.get(choice - 1).getBookingTime())) + ":00";

                    }

                    if (!f) {
                        logger.severe("Chosen Day Is Full");

                        continue;

                    }

                    else {
                        break;
                    }


                }

                while (true)
                {
                    logger.info("Choose The Time You Want To Book Event At : ");


                    int choseTime;

                    choseTime = scanner.nextInt();
                     ChosenTime = Times.get(choseTime-1);
                    if(ChosenTime == null)
                    {
                        logger.severe("Wrong option\n");
                        continue;
                    }

                    else
                        break;
                }

                Balance-=Integer.parseInt(AllEvent.get(choice - 1).getPrice());

                logger.info("Enter Description : ");

                Description = reader.readLine();





























                break;


            }

            else {
                logger.info("Enter A Valid Number\n");
            }

        }




    }

    private static void serviceproviderpage() throws IOException, SQLException {
        logger.info("***************************ServiceProvider Page***************************\n");
        int ch;
        boolean continueloopbig = true;
        while(continueloopbig) {
            logger.info("1- Event Service Management\n" +
                    "2- Venue Management");
            ch = scanner.nextInt();
            if (ch == 1) {
                logger.info("***************************Event Service Management***************************\n");

                boolean continueloop = true;
                while (continueloop) {
                    int che;
                    logger.info("1- Add EventService\n" +
                            "2- Edit EventService\n" +
                            "3- Delete EventService");
                    che = scanner.nextInt();
                    if (che == 1) {
                        addeventservice();
                        return;

                    } else if (che == 2) {
                        editeventservice();
                        return;

                    } else if (che == 3) {
                        deleteeventservice();
                        return;

                    } else {
                        logger.info("You should choose number above");
                        logger.info("Do you want to continue? (yes/no)");
                        String userInput = reader.readLine();
                        continueloop = userInput.equals("yes");
                    }

                }


            } else if (ch == 2) {
                logger.info("***************************Venue Management***************************\n");

                boolean continueloop = true;
                while (continueloop) {
                    int che;
                    logger.info("1- Add Venue\n");
                    che = scanner.nextInt();
                    if (che == 1) {
                        addvenu();
                        return;

                    } else {
                        logger.info("You should choose number above");
                        logger.info("Do you want to continue? (yes/no)");
                        String userInput = reader.readLine();
                        continueloop = userInput.equals("yes");
                    }

                }

            } else {
                logger.info("You should choose number above");
                logger.info("Do you want to continue? (yes/no)");
                String userInput = reader.readLine();
                continueloopbig = userInput.equals("yes");
            }

        }

    }



    private static void addvenu() throws IOException, SQLException {


        String Name;
        String Capacity;
        String Amenities;
        Places place = new Places();

        logger.info("Enter Venue Name  : ");
       Name = reader.readLine();
        logger.info("Enter Capacity  : ");
        Capacity = reader.readLine();
        logger.info("Enter Amenities  : ");
        Amenities = reader.readLine();

        place.setName(Name);
        place.setCapacity(Capacity);
        place.setAmenities(Amenities);
        eventManipulation.addvenue(place);
        logger.info(eventManipulation.getStatus());
        serviceproviderpage();

    }

    private static void deleteeventservice() throws SQLException, IOException {
        List<EventService> AllEvent = retrieve.retrieveAllEventServices();

        logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                "Id", "Title", "Details", "EventCategory", "Price", "Place", "StartTime", "EndTime", "BookingTime"));
        for (EventService eventService : AllEvent) {
            logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                    eventService.getId(), eventService.getTitle(), eventService.getDetails(),
                    eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                    eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));
        }

        int event_id;
        logger.info("Enter The Event_Id you want To Delete : ");
        event_id = scanner.nextInt();
        EventService es = new EventService();
        es.setId(event_id);
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

        List<EventService> AllEvent = retrieve.retrieveAllEventServices();

        logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                "Id", "Title", "Details", "EventCategory", "Price", "Place", "StartTime", "EndTime", "BookingTime"));
        for (EventService eventService : AllEvent) {
            logger.info(String.format("%-15s%-20s%-15s%-15s%-15s%-15s%-15s%-15s%-15s%n",
                    eventService.getId(), eventService.getTitle(), eventService.getDetails(),
                    eventService.getEventCategory(), eventService.getPrice(), eventService.getPlace(),
                    eventService.getStartTime(), eventService.getEndTime(), eventService.getBookingTime()));
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
                logger.info("Do you want to continue? (yes/no)");
                String userInput = reader.readLine();
                continueLoop = userInput.equals("yes");

            }



        }
        exit(0);

    }

    private static void adminpage() {
        logger.info("ad page");

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
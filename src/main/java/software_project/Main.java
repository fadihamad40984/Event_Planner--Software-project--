package software_project;


import software_project.DataBase.DB_Connection;
import software_project.DataBase.retrieve.retrieve;
import software_project.EventManagement.EventManipulation;
import software_project.EventManagement.EventService;
import software_project.UserManagement.User;
import software_project.authentication.Login;
import software_project.authentication.Register;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.logging.*;

import static java.lang.System.exit;

public class Main {
private static JFileChooser fileChooser = new JFileChooser();

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

   private static Scanner scanner = new Scanner(System.in);
    private static DB_Connection conn = new DB_Connection();
    private static Logger logger = Logger.getLogger(Main.class.getName());
    static {

        System.setProperty("mail.debug", "false");


        Logger rootLogger = Logger.getLogger("");
        for (Handler handler : rootLogger.getHandlers()) {
            if (handler instanceof ConsoleHandler) {
                handler.setLevel(Level.OFF);
            }
        }
    }

    private static String username;
    private static String password;
    private static Login login = new Login(conn.getCon());
    private static Register register = new Register(conn.getCon());
    private static EventManipulation eventManipulation = new EventManipulation(conn.getCon());

     private static retrieve retrieve = new retrieve(conn.getCon());
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

    }

    private static void signinpage() throws IOException, SQLException {
        boolean continueLoop = true;

        while (continueLoop)
        {
            logger.info("Enter UserName : ");
            username = reader.readLine();

            logger.info("Enter Password : ");

            password = reader.readLine();

            boolean b =  login.loginUser(username,password);
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

    private static void customerpage() {
        logger.info("cos page");

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
                    logger.info("1- Add Venue\n" +
                            "2- Edit Venue\n" +
                            "3- Delete Venue");
                    che = scanner.nextInt();
                    if (che == 1) {
                        addvenu();
                        return;

                    } else if (che == 2) {
                        editvenue();
                        return;

                    } else if (che == 3) {
                        deletevenue();
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

    private static void editvenue() {
    }

    private static void deletevenue() {
    }

    private static void addvenu() {
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

    private static void editeventservice() {
        
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
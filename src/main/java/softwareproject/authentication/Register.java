package softwareproject.authentication;


import softwareproject.database.insert.InsertData;
import softwareproject.database.retrieve.Retrieveuser;
import softwareproject.usermanagement.User;
import softwareproject.helper.Validation;

import java.sql.*;
import java.util.List;

public class Register {
    private String status;
    private final InsertData usersInserter;
    private final Retrieveuser usersRetriever;

    public Register(Connection connection) {
        usersInserter = new InsertData(connection);
        usersRetriever = new Retrieveuser(connection);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }



    public void registerUser(User user) throws SQLException {
        String st = Validation.rollValidationTest(user);
        setStatus(st);
        if(getStatus().equals("Valid")){

            List <User> alluser = usersRetriever.findUserByUsername(user.getUsername());

            if(alluser == null || alluser.isEmpty()){
                usersInserter.insertUser(user);
                setStatus("User was registered successfully");
            }


            else setStatus("Username is already taken");



        }



    }
}
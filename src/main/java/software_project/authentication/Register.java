package software_project.authentication;

import software_project.DataBase.DB_Connection;
import software_project.DataBase.insert.insertData;
import software_project.DataBase.retrieve.retrieveuser;
import software_project.UserManagement.User;
import software_project.helper.validation;

import java.sql.*;
import java.util.List;

public class Register {
    private String status;
    private insertData usersInserter;
    private retrieveuser usersRetriever;

    public Register(Connection connection){
        usersInserter = new insertData(connection);
        usersRetriever = new retrieveuser(connection);
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void registerUser(User user) throws SQLException {
        String st = validation.rollValidationTest(user);
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
package software_project.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private String databaseName;
    private String username;
    private String password;
    private String status;
    private int port;
    private Connection con;

    public DBConnection() {
        setPort(5432);
        setDatabaseName("Event_Planner");
        setUsername("postgres");
        setPassword("admin");
        setCon();
    }

    public DBConnection(int port, String databaseName, String username, String password) {
        setPort(port);
        setDatabaseName(databaseName);
        setUsername(username);
        setPassword(password);
        setCon();
    }

    public String getStatus() {
        return status;
    }

    public Connection getCon() {
        return con;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public void setCon() {
        String url = "jdbc:postgresql://localhost:" + port + "/" + databaseName;
        try {
            con = DriverManager.getConnection(url, username, password);
            setStatus("Connected To The Database Successfully");
        } catch (Exception e) {
            setStatus("Couldn't Connect To The Database");
        }
    }

}

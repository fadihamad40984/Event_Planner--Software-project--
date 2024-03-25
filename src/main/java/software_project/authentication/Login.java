package software_project.authentication;

import software_project.DataBase.retrieve.retrieveuser;
import software_project.UserManagement.User;
import software_project.helper.UserSession;

import java.sql.Connection;
import java.util.List;

public class Login {
    private String status;
    private retrieveuser usersRetriever;

    public String user_type;
    public Login(Connection connection) {
        usersRetriever = new retrieveuser(connection);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean loginUser(String username, String password) {
        List<User> allUsers = usersRetriever.findUserByUsername(username.toLowerCase());
        if (allUsers != null && !allUsers.isEmpty()) {
            User tmpUser = allUsers.get(0);
            if (tmpUser.getPassword().equals(password)) {
                setStatus("Valid username and password");
                user_type = tmpUser.getUserType();
                UserSession.setCurrentUser(tmpUser);
                UserSession.setSessionId(UserSessionManager.createSession(username));
                return true;
            }
        }
        setStatus("Invalid username or password");
        return false;
    }
}

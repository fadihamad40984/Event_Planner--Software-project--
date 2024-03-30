package softwareproject.authentication;

import softwareproject.database.retrieve.Retrieveuser;
import softwareproject.usermanagement.User;
import softwareproject.helper.UserSession;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Login {
    private String status;
    private final Retrieveuser usersRetriever;

    private String userType;
    public Login(Connection connection) {
        usersRetriever = new Retrieveuser(connection);
    }

    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public boolean loginUser(String username, String password) throws SQLException {
        List<User> allUsers = usersRetriever.findUserByUsername(username.toLowerCase());
        if (allUsers != null && !allUsers.isEmpty()) {
            User tmpUser = allUsers.get(0);
            if (tmpUser.getPassword().equals(password)) {
                setStatus("Valid username and password");
                setUserType(tmpUser.getUserType());
                UserSession.setCurrentUser(tmpUser);
                UserSession.setSessionId(UserSessionManager.createSession());
                return true;
            }
        }
        setStatus("Invalid username or password");
        return false;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}

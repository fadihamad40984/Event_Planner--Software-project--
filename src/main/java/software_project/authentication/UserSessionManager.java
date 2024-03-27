package software_project.authentication;

import software_project.helper.UserSession;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UserSessionManager {
    private static final Logger logger = Logger.getLogger(UserSessionManager.class.getName());
    private static final List<String> activeSessions = new ArrayList<>();
    private static String status;

    private UserSessionManager() {
        throw new IllegalStateException("Utility class");
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        UserSessionManager.status = status;
    }

    public static String createSession() {
        String sessionId = generateSessionId();
        activeSessions.add(sessionId);
        return sessionId;
    }

    public static boolean isValidSession(String sessionId) {
        return activeSessions.contains(sessionId);
    }

    private static String generateSessionId() {
        return "SESSION_" + System.currentTimeMillis();
    }

    public static void invalidateSession(String sessionId) {
        activeSessions.remove(sessionId);
       logger.info("Session invalidated for Session ID: " + sessionId);
    }

    public static void logoutUser() {
        UserSessionManager.invalidateSession(UserSession.getSessionId());
        UserSession.setSessionId(null);
        logger.info("Logged out");
    }
}

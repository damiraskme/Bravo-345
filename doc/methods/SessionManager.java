import java.util.ArrayList;

public class SessionManager {
    AccountDatabase account = new AccountDatabase();
    ArrayList<Session> sessions = new ArrayList<Session>();

    public SessionManager() {

    }

    Session login(String loginName, String password, Role role) throws InvalidCredentialsException {
        Session session = new Session(role, this);
        if(account.checkCredentials(loginName, password, role)) {
            sessions.add(session);
            return session;
        }
        throw new InvalidCredentialsException(loginName);
    }

    public class InvalidCredentialsException extends Exception {
        public InvalidCredentialsException(String error) {
            super(error);
        } 
    }
}

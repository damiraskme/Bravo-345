import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Session {
    private Date expTime;
    final private long timer = 3000L;
    public Role role;
    public AccountDatabase account;
    public StudentAccount studentAcc;
    public SessionManager manager;
    private Timer logoutTimer;

    public class StudentAccount {
            // empty for now
    }
    

    public Session(Role role, SessionManager manager) {
        this.role = role;
        this.manager = manager;
        this.expTime = new Date(System.currentTimeMillis() + timer);
        logoutTimer = new Timer();
        logoutTimer.schedule(new LogoutTask(), this.expTime);
    }

    public void logout() {
        if (logoutTimer != null) {
            logoutTimer.cancel();
            SessionManager manager = new SessionManager();
            manager.sessions.remove(this);
            System.out.println("Session deleted");
            System.out.println(manager.sessions);
        }
    }
    private class LogoutTask extends TimerTask {
        @Override
        public void run() {
            logout();
        }
    }
}

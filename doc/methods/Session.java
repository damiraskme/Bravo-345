import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Session {
    public static  String StudentAccount;
    private Date expTime;
    final private long timer = 10000L;
    public Role role;
    public AccountDatabase account;
    public StudentAccount studentAcc;
    public SessionManager manager;
    private Timer logoutTimer;
    private boolean isActive = true;
    public Object studentAccount;

    //public StudentAccount studentAccount;
    
    public class StudentAccount {
        private int id;
        private String username;
        private String password;
        private Role role;
        private String status;
    
        public StudentAccount(int id, String username, String password, Role role, String status) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.role = role;
            this.status = status;
        }
    
        public int getId() {
            return id;
        }
    
        public String getUsername() {
            return username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public Role getRole() {
            return role;
        }
    
        public String getStatus() {
            return status;
        }
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
    public boolean isActive() {
        return isActive;
    }

    public Role getRole() {
        return role;
    }

    public void expire() {
        isActive = false;
    }

    public Object getUsername() {
        //throw new UnsupportedOperationException("Unimplemented method 'getUsername'");
        return studentAcc.getUsername();
    }
}

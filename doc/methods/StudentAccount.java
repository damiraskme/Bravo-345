package Test;

import java.util.Map;
import java.time.Year;
import java.util.HashMap;

public class StudentAccount {
    private int id;
    private String login;
    private String password;
    private Role role;
    private Map<String, Integer> dob = new HashMap<>();
    private char gend;
    private String status;
    private AcademicHistory history;
    private String phone;

    StudentAccount() {
        this.dob.put("Year", null);
        this.dob.put("Month", null);
        this.dob.put("Day", null);
        this.gend = ' ';
        this.history = null;
        this.phone = null;
    }

    StudentAccount(int year, int month, int day, char gend, AcademicHistory hist, String phone, Role role) {
        this.dob.put("Year", year);
        this.dob.put("Month", month);
        this.dob.put("Day", day);
        this.gend = gend;
        this.history = hist;
        this.phone = phone;
        this.role = role;
    }

    StudentAccount(int id, String login, String pass, Role role, String status) {
        this.id = id;
        this.login = login;
        this.password = pass;
        this.role = role;
        this.status = status;
    }

    StudentAccount(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public void setDob(Session session, int year, int month, int day) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        else if (year > Year.now().getValue() || year < 1950 || day < 1 || month < 1 || day > 31 || month > 12) {
            throw new Exception("Wrong date format");
        }
        this.dob.put("Year", year);
        this.dob.put("Month", month);
        this.dob.put("Day", day);
    }

    public void setRole(Session session, Role role) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role) || this.role != Role.ADMINISTRATOR || this.role != Role.FACULTY) {
            throw new AccessViolationException("Access Violation");
        }
        
        this.role = role;
    }
    public void setStatus(Session session, String status) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role) || this.role != Role.ADMINISTRATOR || this.role != Role.FACULTY) {
            throw new AccessViolationException("Access Violation");
        }
        
        this.status = status;
    }

    public void setPhone(Session session, String phone) throws Exception {
        
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        else if (phone.charAt(0) != '+' || phone.length() != 10) {
            throw new Exception("Wrong phone number format");
        }
        this.phone = phone;
    }

    public void setHist(Session session, AcademicHistory history) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role) || this.role != Role.ADMINISTRATOR || this.role != Role.FACULTY) {
            throw new AccessViolationException("Access Violation");
        }
        this.history = history;
    }

    public void setGend(Session session, char gend) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        else if (gend != 'M' || gend != 'F' || gend != 'O') {
            throw new Exception("Wrong gender format");
        }
        this.gend = gend;
    }

    public void setId(Session session, int id) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role) || this.role != Role.ADMINISTRATOR || this.role != Role.FACULTY) {
            throw new AccessViolationException("Access Violation");
        }
        this.id = id;
    }

    public void setLogin(Session session, String login) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role) || this.role != Role.ADMINISTRATOR || this.role != Role.FACULTY) {
            throw new AccessViolationException("Access Violation");
        }
        this.login = login;
    }

    public void setPass(Session session, String password) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        else if(!db.passwordCheck(password)) {
            throw new Exception("Wrong password format");
        }
        this.password = password;
    }

    public String getDob(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.dob.toString();
    }

    public String getPhone(Session session) throws Exception {
        
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.phone;
    }

    public AcademicHistory getHist(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.history;
    }

    public char getGend(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.gend;
    }

    public int getId(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.id;
    }

    public String getLogin(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.login;
    }
    
    public String getPass(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        else if(!db.passwordCheck(password)) {
            throw new Exception("Wrong password format");
        }
        return this.password;
    }

    public Role getRole(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        
        return this.role;
    }
    public String getStatus(Session session) throws Exception {
        AccountDatabase db = new AccountDatabase();
        if(!session.isActive()) {
            throw new ExpiredSessionException("Expired session");
        }
        else if (!db.checkCredentials(this.login, this.password, this.role)) {
            throw new AccessViolationException("Access Violation");
        }
        return this.status;
    }
}

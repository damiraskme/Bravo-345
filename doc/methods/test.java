
public class test {
    public static void main(String[] args) {
        AccountDatabase db = new AccountDatabase();
        System.out.println("AccountDatabase class:");
        System.out.println("Correct credentials:");
        System.out.println(db.checkCredentials("test test1", "12", Role.ADMINISTRATOR));
        System.out.println("Wrong credentials:");
        System.out.println(db.checkCredentials("test test3", "12333", Role.ADMINISTRATOR));
        System.out.println("Blocked account:");
        System.out.println(db.checkCredentials("test test2", "123", Role.STUDENT));

        db.block(1);
        db.unblock(2);
        System.out.println("Account changed to blocked:");
        System.out.println(db.checkCredentials("test test1", "12", Role.ADMINISTRATOR));
        System.out.println("Account changed to active:");
        System.out.println(db.checkCredentials("test test2", "123", Role.STUDENT));


        System.out.println("SessionManager class:");
        SessionManager manager = new SessionManager();
        try {
            System.out.println("Correct login:");
            manager.login("test test2", "123", Role.STUDENT);
            System.out.println("Incorrect login:");
            manager.login("test test2", "123456", Role.STUDENT);
            System.out.println("Blocked login:");
            manager.login("test test1", "123", Role.ADMINISTRATOR);
        } catch (Exception e) {
            System.out.println("Error");
        }
        System.out.println("Session class:");
        Session session = new Session(Role.STUDENT, manager);
        System.out.println("Session created");
        db.block(2);
        db.unblock(1);
    }
}

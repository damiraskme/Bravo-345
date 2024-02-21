import java.util.Scanner;

public class prototype {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your role? (student, advisor, administrator)");
        String role = scanner.nextLine(); 

        switch (role.toLowerCase()) { 
            case "student":
                studentOptions(scanner);
                break;
            case "advisor":
                advisorOptions(scanner);
                break;
            case "administrator":
                administratorOptions(scanner);
                break;
            default:
                System.out.println("Invalid role entered. Please enter student, advisor, or administrator.");
                break;
        }

        scanner.close();
    }

    private static void studentOptions(Scanner scanner) {
        System.out.println("*System requires student verification*");

        System.out.println("Welcome, student!");

        int choice;

        do{
            System.out.println("1) Student Profile");
            System.out.println("2) Academic Planning");
            System.out.println("3) Course Registration");
            System.out.println("4) Academic Advisor Interaction");
            System.out.println("5) Degree Requirements");
            System.out.println("6) Course Recommendations");
            System.out.println("7) Notifications and Alerts");
            System.out.println("8) Document Management");
            System.out.println("9) Help and Support");
            System.out.println("10) Documentation");
            System.out.println("0) Logout");

            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("You selected Student Profile.");
                    System.out.println("*Students shall be able to create and update their profiles");
                    System.out.println("Profile information shall include personal details, academic history, and contact information.*");
                    break;
                case 2:
                    System.out.println("You selected Academic Planning.");
                    System.out.println("*Students shall be able to view their current academic progress, including completed and in-progress courses.*");
                    System.out.println("*Students shall be able to set academic goals and track their progress towards graduation*");
    
                    break;
    
                case 3:
                    System.out.println("You selected Course Registration.");
                    System.out.println("*Students shall be able to search for available courses, view course details, and register for courses*");
                    System.out.println("*The system shall check prerequisites and course availability during registration*");
    
                    break;
    
                case 4:
                    System.out.println("You selected Academic Advisor Interaction.");
                    System.out.println("*Students shall be able to request meetings with their academic advisors*");
    
                    break;
    
                case 5:
                    System.out.println("You selected Degree Requirements.");
                    System.out.println("*The system shall maintain a list of degree programs and their respective requirements*");
                    System.out.println("*Students shall be able to view their degree requirements and track their progress towards degree completion*");
    
                    break;
    
                case 6:
                    System.out.println("You selected Course Recommendation.");
                    System.out.println("*The system will show the course recommendation after reviewing student's goal, academic hostory, and major requirment*");
                    break;
                case 7:
                    System.out.println("You selected Notifications and Alerts.");
    
                    System.out.println("*The system shall send notifications and alerts to students regarding registration deadlines, academic standing, and important academic events*");
                    break;
                case 8:
                    System.out.println("You selected Document Management.");
    
                    System.out.println("1) Upload Document");
                    System.out.println("2) Store Document");
                    int choice_2 = Integer.parseInt(scanner.nextLine());
                        switch (choice_2) {
                            case 1:
                                System.out.println("*Student can upload the document*");
                                break;
                            case 2:
                                System.out.println("*Student can store the ducument*");
                                break;
                            default:
                                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                                break;
                    }
                    break;
                case 9:
                    System.out.println("You selected Help and Support.");
                    System.out.println("*The system shall provide a help center or FAQ section to assist users with common queries*");
    
                    System.out.println("*The system shall offer customer support options for technical issues and inquiries*");
    
    
                    break;
                case 10:
                    System.out.println("You selected Documentation");
                    System.out.println("*The system shall provided comprehensive documentation for system administrators, advisors, and students*");
    
                    break;
    
                default:
                    System.out.println("Invalid choice. Please select from 1 to 10.");
                    break;
            }
        }while(choice != 0);
        System.out.println("You have logged out.");
    }

    private static void advisorOptions(Scanner scanner) {
        System.out.println("*System requires administrator verification*");

        System.out.println("Welcome, advisor!");
        int choice;

        do{
            System.out.println("1) Reporting and Analytics");
            System.out.println("2) Academic Advisor Interaction");
            System.out.println("3) Course Registration");
            System.out.println("4) Help and Support");
            System.out.println("5) Document Management");
            System.out.println("6) Documentation");
            System.out.println("0) Logout");
            choice = Integer.parseInt(scanner.nextLine());
        switch (choice) {
            case 1:
                System.out.println("You selected Reporting and Analytics.");
                System.out.println("*Advisors shall have access to reports and analytics on student performance, course enrollment trends, and graduation rates*");

                break;
            case 2:
                System.out.println("You selected Academic Advisor Interaction.");
                System.out.println("*Advisors shall be able to schedule and manage advising appointments*");
                System.out.println("*Advisors shall have access to student profiles and academic records for advising purposes*");
                break;
            case 3:
                System.out.println("You selected Course Registration.");
                System.out.println("*Advisors shall be able to search for courses, view course details, and see registered students*");
                break;
            case 4:
                System.out.println("You selected Help and Support.");
                System.out.println("*The system shall provide a help center or FAQ section to assist users with common queries*");
                System.out.println("*The system shall offer customer support options for technical issues and inquiries*");

            case 5:
                System.out.println("You selected Document Management.");
                System.out.println("1) Upload Document");
                System.out.println("2) Store Document");
                int choice_2 = Integer.parseInt(scanner.nextLine());
                    switch (choice_2) {
                        case 1:
                            System.out.println("*Advisor can upload the document*");
                            break;
                        case 2:
                            System.out.println("*Advisor can store the ducument*");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select 1, 2, or 3.");
                            break;
                }
                break;
            case 6:
                System.out.println("You selected Documentation");
                System.out.println("*The system shall provided comprehensive documentation for system administrators, advisors, and students*");
                break;

            default:
                System.out.println("Invalid choice. Please select from 1 to 6.");
                break;
            }
        }while(choice != 0);
    }

    private static void administratorOptions(Scanner scanner) {
        System.out.println("*System requires administrator verification*");
        System.out.println("Welcome, administrator!");

        int choice;

        do{
            System.out.println("1) Course Registration");
            System.out.println("2) Reporting and Analytics");
            System.out.println("3) Administrative Features");
            System.out.println("4) Security and Data Privacy");
            System.out.println("5) Documentation");
            System.out.println("6) Help and Support");
            System.out.println("0) Logout");
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("You selected Course Registration.");
                    System.out.println("*Administrator shall be able to search for courses, view course details, and change course information*");
                    break;
                case 2:
                    System.out.println("You selected Reporting and Analytics.");
                    System.out.println("*Administrator shall have access to reports and analytics on student performance, course enrollment trends, and graduation rates*");
                    break;
                case 3:
                    System.out.println("You selected Administrative Features.");
                    System.out.println("1) Manage User Account");
                    System.out.println("2) Reset Passwords");
                    System.out.println("3) View System Logs");
                    System.out.println("4) Configure System Settings and Policies");


                    int choice_2 = Integer.parseInt(scanner.nextLine());
                        switch (choice_2) {
                            case 1:
                                System.out.println("*Administrators shall be able to manage user accounts");
                                break;
                            case 2:
                                System.out.println("*Administrators shall be able to reset passwords*");
                                break;
                            case 3:
                                System.out.println("*Administrators shall be able to view system logs*");
                                break;
                            case 4:
                                System.out.println("*Administrators shall be able to configure system settings and policies*");
                                break;
                            default:
                                System.out.println("Invalid choice. Please select from 1 to 4.");
                                break;
                    }
                    break;

                case 4:
                    System.out.println("You selected Security and Data Privacy.");
                    System.out.println("*The system shall implement security measures to protect student data and ensure data privacy compliance*");
                    break;
                case 5:
                    System.out.println("You selected Documentation");
                    System.out.println("*The system shall provided comprehensive documentation for system administrators, advisors, and students*");
                    break;
                case 6:
                    System.out.println("You selected Help and Support.");
                    System.out.println("*The system shall provide a help center or FAQ section to assist users with common queries*");
                    System.out.println("*The system shall offer customer support options for technical issues and inquiries*");
                    break;
                case 0:
                    System.out.println("You have logged out.");
                    break;
                default:
                    System.out.println("Invalid choice. Please select from 1 to 6.");
                    break;
            }
        }while(choice != 0);
    }
}

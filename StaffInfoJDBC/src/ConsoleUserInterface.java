import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ConsoleUserInterface extends UserInterface {
    private final StaffRepository staffRepository;
    Scanner scanner = UserInput.getInstance();

    public ConsoleUserInterface(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public void showOptions() {
        System.out.println("1. View a record by id");
        System.out.println("2. View All");
        System.out.println("3. Insert");
        System.out.println("4. Update");
        System.out.println("5. Delete");
        System.out.println("6. Exit");
        System.out.print("Select an option: ");
    }

    @Override
    public void executeOption(int option) throws SQLException {
        Staff staff = null;
        String id;
        switch (option) {
            case 1:
                System.out.print("Enter ID to view: ");
                id = scanner.next();
                System.out.println(staffRepository.getStaffById(id).toString());
                break;
            case 2:
                List<Staff> staffList = staffRepository.getAllStaff();
                printStaffList(staffList);
                break;
            case 3:
                staff = readStaff();
                staffRepository.insertStaff(staff);
                break;
            case 4:
                System.out.print("Enter ID to view: ");
                id = scanner.next();
                staff = readStaff();
                staffRepository.updateStaff(staff);
                break;
            case 5:
                System.out.print("Enter ID to delete: ");
                id = scanner.next();
                staffRepository.deleteStaff(id);
                break;
            case 6:
                System.exit(0);
            default:
                System.out.println("Invalid option. Please try again.");
                break;
        }
    }

    private void printStaffList(List<Staff> staffList) {
        int idx = 0;
        while(idx<staffList.size()){
            Staff staff = staffList.get(idx++);
            System.out.println("Row 1:");
            System.out.println(staff.toString());
        }
    }
    private Staff readStaff(){
        System.out.println("Enter staff information:");

        System.out.print("ID: ");
        String id = scanner.next();

        System.out.print("Last Name: ");
        String lastName = scanner.next();

        System.out.print("First Name: ");
        String firstName = scanner.next();

        System.out.print("MI: ");
        char mi = scanner.next().charAt(0);

        System.out.print("Age: ");
        int age = scanner.nextInt();

        System.out.print("Address: ");
        String address = scanner.next();

        System.out.print("City: ");
        String city = scanner.next();

        System.out.print("State: ");
        String state = scanner.next();

        System.out.print("Telephone: ");
        String telephone = scanner.next();

        System.out.print("Email: ");
        String email = scanner.next();

        Staff newStaff = new Staff(id, lastName, firstName, mi, age, address, city, state, telephone, email);
        return newStaff;
    }
    
}

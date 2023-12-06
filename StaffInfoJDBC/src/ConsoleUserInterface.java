import java.sql.SQLException;
import java.util.HashSet;
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
                if(staff != null){
                    staffRepository.insertStaff(staff);
                }
                break;
            case 4:
                System.out.print("Enter ID to update: ");
                id = scanner.next();
                System.out.print("Enter column name to update: ");
                String col = scanner.next();
                System.out.print("Enter column value to update: ");
                String colVal = scanner.next();
                if(updateDataValidation(col,colVal)){
                    staffRepository.updateStaff(col, colVal, id);
                }
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
        if (!validateID(id)) {
            return null;
        }

        System.out.print("Last Name: ");
        String lastName = scanner.next();
        if (!validateName(lastName)) {
            return null;
        }

        System.out.print("First Name: ");
        String firstName = scanner.next();
        if (!validateName(firstName)) {
            return null;
        }

        System.out.print("MI: ");
        String mi = scanner.next();
        if (!validateMi(mi)) {
            return null;
        }

        System.out.print("Age: ");
        String ageStr = scanner.next();
        if (!isInteger(id)) {
            System.out.println("Invalid address format. Please enter a valid address.");
            return null;
        }
        int age = Integer.parseInt(ageStr);

        System.out.print("Address: ");
        String address = scanner.next();
        if (!validateAddress(address)) {
            return null;
        }

        System.out.print("City: ");
        String city = scanner.next();
        if (!validateCity(city)) {
            return null;
        }

        System.out.print("State: ");
        String state = scanner.next();
        if (!validateState(state)) {
            return null;
        }

        System.out.print("Telephone: ");
        String telephone = scanner.next();
        if (!isValidPhoneNumber(telephone)) {
            System.out.println("Invalid telephone number. Please enter a valid 10-digit phone number.");
            return null;
        }
        System.out.print("Email: ");
        String email = scanner.next();
        if (!isValidEmail(email)) {
            System.out.println("Invalid email format. Please enter a valid email address.");
            return null;
        }

        Staff newStaff = new Staff(id, lastName, firstName, mi.charAt(0), age, address, city, state, telephone, email);
        return newStaff;
    }

    /*================================================Input Validation=============================================================== */
    private static boolean isValidEmail(String email) {
        // Simple email validation using a regular expression
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }

    private static boolean isValidPhoneNumber(String phoneNumber) {
        // Simple telephone number validation: checks if it contains only digits and has a length of 10
        return phoneNumber.matches("\\d{10}");
    }
    public boolean isInteger(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    private boolean updateDataValidation(String column, String colVal){
        HashSet<String> columns = new HashSet<String>();
        columns.add("id");
        columns.add("lastName");
        columns.add("firstName");
        columns.add("mi");
        columns.add("age");
        columns.add("address");
        columns.add("city");
        columns.add("state");
        columns.add("telephone");
        columns.add("email");
        if (columns.contains(column)){
            switch(column){
                case "id":
                    if (!isInteger(colVal)) {
                        System.out.println("Invalid value for id. Enter integer value");
                        return false;
                    }
                    break;
                case "lastName":
                    if (!validateName(colVal)) {
                        return false;
                    }
                    break;
                case "firstName":
                    if (!validateName(colVal)) {
                        return false;
                    }
                    break;
                case "mi":
                    if (!validateMi(colVal)) {
                        return false;
                    }
                    break;
                case "age":
                    if (!isInteger(colVal)) {
                        System.out.println("Invalid value for age. Enter integer value.");
                        return false;
                    }
                    break;
                case "address":
                    if (!validateAddress(colVal)) {
                        return false;
                    }
                    break;
                case "city":
                    if (!validateCity(colVal)) {
                        return false;
                    }
                    break;
                case "state":
                    if (!validateState(colVal)) {
                        return false;
                    }
                    break;
                case "telephone":
                    if (!isValidPhoneNumber(colVal)) {
                        System.out.println("Invalid telephone number. Please enter a valid 10-digit phone number.");
                        return false;
                    }
                    break;
                case "email":
                if (!isValidEmail(colVal)) {
                        System.out.println("Invalid email address.");
                        return false;
                    }
                    break;
            }
            return true;
        }else{
            System.out.println("Column not found. Columns in Staff table are: " + columns);
            return false;
        }
    }
    private boolean validateID(String id){
        if (!isInteger(id) || id.length() > 9) {
            System.out.println("Invalid value for ID. Enter integer value and length should be less than 9 digit.");
            return false;
        }
        return true;
    }
    private boolean validateName(String name){
        if (!name.matches("[a-zA-Z]+") || name.length() > 15) {
            System.out.println("Invalid value for Name. Enter string value and length should be less than 15 characters");
            return false;
        }
        return true;
    }
    private boolean validateMi(String mi){
        if ( !mi.matches("[a-zA-Z]") || mi.length() > 1) {
            System.out.println("Invalid value for mi. Enter single alphabetic character");
            return false;
        }
        return true;
    }
    private boolean validateAddress(String address){
        if ( !address.matches("^[a-zA-Z0-9\\s]+$") || address.length() > 20) {
            System.out.println("Invalid Address. Enter address with length 20 or less");
            return false;
        }
        return true;
    }
    private boolean validateCity(String city){
        if (!city.matches("[a-zA-Z]+") || city.length() > 15) {
            System.out.println("Invalid value for City. Enter string value and length should be 20 characters or less");
            return false;
        }
        return true;
    }
    private boolean validateState(String state){
        if (!state.matches("[A-Z]+") || state.length() > 2) {
            System.out.println("Invalid value for Name. Capital letters only. Length should be 2 characters or less");
            return false;
        }
        return true;
    }
}

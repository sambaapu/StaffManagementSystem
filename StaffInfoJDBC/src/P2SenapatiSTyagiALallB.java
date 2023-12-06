import java.sql.SQLException;
import java.util.Scanner;

public class P2SenapatiSTyagiALallB {

    public static void main(String[] args) {
        StaffRepository staffRepository = new StaffRepositoryMySQLDB();
        UserInterface userInterface = new ConsoleUserInterface(staffRepository);

        while (true) {
            userInterface.showOptions();
            Scanner scanner = UserInput.getInstance();;
            int choice = scanner.nextInt();
            try {
                userInterface.executeOption(choice);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
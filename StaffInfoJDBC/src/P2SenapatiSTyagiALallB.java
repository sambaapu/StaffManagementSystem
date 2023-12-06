import java.sql.SQLException;
import java.util.Scanner;

public class P2SenapatiSTyagiALallB {

    public static void main(String[] args) {
        StaffRepository staffRepository = new StaffRepositoryMySQLDB();
        UserInterface userInterface = new ConsoleUserInterface(staffRepository);

        while (true) {
            System.out.println("++++++++++++++++++++New Instance++++++++++++++++++++++++++++");
            userInterface.showOptions();
            Scanner scanner = UserInput.getInstance();;
            int choice = scanner.nextInt();
            try {
                userInterface.executeOption(choice);
                System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
                System.out.println();
                System.out.println();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
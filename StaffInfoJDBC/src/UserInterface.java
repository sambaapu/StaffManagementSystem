import java.sql.SQLException;

public abstract class UserInterface {
    abstract void showOptions();

    abstract void executeOption(int option) throws SQLException;
}

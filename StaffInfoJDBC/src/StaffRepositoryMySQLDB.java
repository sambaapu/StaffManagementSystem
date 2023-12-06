import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaffRepositoryMySQLDB implements StaffRepository {
    private static final String DB_URL = "jdbc:mysql://localhost/StaffInfo";
    private static final String USER = "root";
    private static final String PASSWORD = "mahasena";
    private Connection con;
    private static List<Staff> staffList = new ArrayList<>();

    public StaffRepositoryMySQLDB(){
        try {
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Staff> getAllStaff() throws SQLException {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Staff");

            while (resultSet.next()) {
                Staff staff = new Staff(
                        resultSet.getString("id"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("mi").charAt(0),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email")
                );
                staffList.add(staff);
            }
            Collections.sort(staffList);
        } catch (SQLException e){
            e.printStackTrace();
        }
        return staffList;
    }

    @Override
    public Staff getStaffById(String id) throws SQLException {
        return findStaffById(id);
    }

    @Override
    public void insertStaff(Staff staff) throws SQLException {
        if(findStaffById(staff.getId())!=null){
            System.out.println("Record with the same id already exists. Insertion failed.");
            return;
        }
        if(isDuplicateRecord(staff.getFirstName(), staff.getLastName(), staff.getAge())){
            System.out.println("Record with the same first name, last name, and age already exists. Insertion failed.");
            return;
        }
        try (PreparedStatement preparedStatement = con.prepareStatement(
                "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, staff.getId());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setString(3, staff.getFirstName());
            preparedStatement.setString(4, String.valueOf(staff.getMi()));
            preparedStatement.setInt(5, staff.getAge());
            preparedStatement.setString(6, staff.getAddress());
            preparedStatement.setString(7, staff.getCity());
            preparedStatement.setString(8, staff.getState());
            preparedStatement.setString(9, staff.getTelephone());
            preparedStatement.setString(10, staff.getEmail());

            preparedStatement.executeUpdate();
        }
        System.out.println("Record inserted successfully.");
    }

    @Override
    public void updateStaff(Staff staff) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(
                "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            preparedStatement.setString(1, staff.getId());
            preparedStatement.setString(2, staff.getLastName());
            preparedStatement.setString(3, staff.getFirstName());
            preparedStatement.setString(4, String.valueOf(staff.getMi()));
            preparedStatement.setInt(5, staff.getAge());
            preparedStatement.setString(6, staff.getAddress());
            preparedStatement.setString(7, staff.getCity());
            preparedStatement.setString(8, staff.getState());
            preparedStatement.setString(9, staff.getTelephone());
            preparedStatement.setString(10, staff.getEmail());

            preparedStatement.executeUpdate();
        }
        System.out.println("Record inserted successfully.");
    }

    @Override
    public void deleteStaff(String id) throws SQLException {
        System.out.println(id);
    }
    /* ===============================HELPER METHODS================================================= */
    private Staff findStaffById(String id) throws SQLException{
        try(Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Staff WHERE id = " + id)){
                if(resultSet.next()){
                    Staff staff = new Staff(
                        resultSet.getString("id"),
                        resultSet.getString("lastName"),
                        resultSet.getString("firstName"),
                        resultSet.getString("mi").charAt(0),
                        resultSet.getInt("age"),
                        resultSet.getString("address"),
                        resultSet.getString("city"),
                        resultSet.getString("state"),
                        resultSet.getString("telephone"),
                        resultSet.getString("email")
                );
                return staff;
                }
            }
            return null;
    }
    public boolean isDuplicateRecord (String firstName, String lastName, int age) throws SQLException{
        try(
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Staff WHERE firstName = " + firstName
                                        + " AND lastName = " + lastName  + " AND age = " + age))
        {
            if(resultSet.next()) return true;
            return false;
        }
    }
}
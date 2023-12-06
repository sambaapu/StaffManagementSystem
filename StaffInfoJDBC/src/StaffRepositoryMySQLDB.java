import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class StaffRepositoryMySQLDB implements StaffRepository {
    private static final String DB_URL = "jdbc:mysql://localhost/StaffInfo";
    private static final String USER = "root";
    private static final String PASSWORD = "mahasena";
    private Connection con;

    public StaffRepositoryMySQLDB(){
        try {
            con = DriverManager.getConnection(DB_URL,USER,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAllStaff() throws SQLException {
        HashMap<String, Staff> newStaffMap = new HashMap<>();
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
                newStaffMap.put(staff.getId(),staff);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        StaffList.staffMap = newStaffMap;
        StaffList.staffList = new ArrayList<>(newStaffMap.values());
        sortStaffList();
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
        StaffList.staffList.add(staff);
        StaffList.staffMap.put(staff.getId(),staff);
        sortStaffList();
    }

    @Override
    public void updateStaff(String col, String colVal, String id) throws SQLException {
        try (PreparedStatement preparedStatement = con.prepareStatement(
                "UPDATE Staff SET " + col + " = ? WHERE id = ?")) 
        {
            if(col.equals("age")){
                 preparedStatement.setInt(1, Integer.parseInt(colVal));
            }else{
                preparedStatement.setString(1, colVal);
            }
            preparedStatement.setString(2, id);

            preparedStatement.executeUpdate();
        }
        System.out.println("Record Updated successfully.");
        Staff staff = StaffList.staffMap.get(id);
        StaffList.staffList.remove(staff);
        StaffList.staffMap.remove(id);
        try {
            String setterStr = "set" + col.substring(0, 1).toUpperCase() + col.substring(1);
            System.out.println(setterStr);
            Method methodToCall;
            if(col.equals("age")){
                methodToCall = Staff.class.getMethod(setterStr, Integer.TYPE);
                methodToCall.invoke(staff,Integer.parseInt(colVal));
            }else{
                methodToCall = Staff.class.getMethod(setterStr, String.class);
                methodToCall.invoke(staff,colVal);
            }
            
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Invalid input or method not found. Setter method not found for "+col);
        }
        StaffList.staffMap.put(staff.getId(),staff);
        StaffList.staffList.add(staff);
        sortStaffList();
    }

    @Override
    public void deleteStaff(String id) throws SQLException {
        if(findStaffById(id)!=null){
            try (PreparedStatement preparedStatement = con.prepareStatement(
                "DELETE FROM Staff WHERE id = ? ;")) {

            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
            }
        System.out.println("Record Deleted successfully.");
        Staff staff = StaffList.staffMap.get(id);
        StaffList.staffList.remove(staff);
        StaffList.staffMap.remove(id);
        return;
        }else{
            System.out.println("No Record with id:" + id + ". Nothing deleted.");
            return;
        }
    }
    /* ===============================HELPER METHODS================================================= */
    private Staff findStaffById(String id) throws SQLException{
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Staff WHERE id = ?");){
                preparedStatement.setString(1, id);
                ResultSet resultSet = preparedStatement.executeQuery();
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
        try(PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM Staff WHERE firstName = ? AND lastName = ? AND age = ?");){
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) return true;
            return false;
        }
    }
    private void sortStaffList(){
        Collections.sort(StaffList.staffList, Comparator.comparingInt(Staff::getAge));
    }
}

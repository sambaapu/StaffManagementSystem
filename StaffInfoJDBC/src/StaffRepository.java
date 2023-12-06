import java.sql.*;
import java.util.List;

// Interface for the data access layer
public interface StaffRepository {
    List<Staff> getAllStaff() throws SQLException;

    Staff getStaffById(String id) throws SQLException;

    void insertStaff(Staff staff) throws SQLException;

    void updateStaff(String col, String colVal, String id) throws SQLException;

    void deleteStaff(String id) throws SQLException;
}

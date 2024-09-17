import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PreparedStatementExampleOracle {
    
    // Method to establish a connection to Oracle Database
    public static Connection getConnection() {
        Connection conn = null;
        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";  // Replace with your Oracle DB details
            String username = "SYSTEM";  // Replace with your Oracle username
            String password = "BCA5D";  // Replace with your Oracle password
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Oracle Database.");
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }
    
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmtSelect = null;
        ResultSet rs = null;

        try {
            conn = getConnection();

            // 1. Inserting using PreparedStatement
            String insertEmp = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(insertEmp);
            
            pstmt.setInt(1, 106); // Setting the value for Eid
            pstmt.setString(2, "Bob Smith"); // Setting the value for Ename
            pstmt.setDouble(3, 70000); // Setting the value for Salary
            pstmt.setString(4, "Texas"); // Setting the value for Address
            pstmt.setInt(5, 3); // Setting the value for Did
            pstmt.executeUpdate();
            System.out.println("Inserted data successfully.");

            // 2. Select Query using PreparedStatement
            String selectQuery = "SELECT * FROM Employee WHERE Did = ?";
            pstmtSelect = conn.prepareStatement(selectQuery);
            pstmtSelect.setInt(1, 3); // Setting the value for Did in the SELECT query
            rs = pstmtSelect.executeQuery();
            
            while (rs.next()) {
                System.out.println(rs.getInt("Eid") + " " + rs.getString("Ename") + " " + rs.getDouble("Salary"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmtSelect != null) pstmtSelect.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

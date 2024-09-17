import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ScrollableResultSetExampleOracle {
    
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
        try (Connection conn = getConnection()) {
            
            // Create a PreparedStatement with scrollable ResultSet
            String selectQuery = "SELECT * FROM Employee";
            PreparedStatement pstmt = conn.prepareStatement(selectQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pstmt.executeQuery();
            
            // Move cursor to the last row
            if (rs.last()) {
                System.out.println("Last Row: " + rs.getString("Ename"));
            } else {
                System.out.println("No rows found.");
            }
            
            // Move cursor to the first row
            if (rs.first()) {
                System.out.println("First Row: " + rs.getString("Ename"));
            } else {
                System.out.println("No rows found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

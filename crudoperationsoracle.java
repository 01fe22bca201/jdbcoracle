import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class crudoperationsoracle {
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // 1. Establish the connection to Oracle Database
            String url = "jdbc:oracle:thin:@localhost:1521:xe";  // Replace with your Oracle DB details
            String username = "SYSTEM";  // Replace with your Oracle username
            String password = "BCA5D";  // Replace with your Oracle password
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to Oracle Database.");

            // 2. Create Statement
            stmt = conn.createStatement();

            // 3. Drop Tables if they exist
            String dropDeptTable = "BEGIN " +
                                   "EXECUTE IMMEDIATE 'DROP TABLE Department'; " +
                                   "EXCEPTION WHEN OTHERS THEN NULL; " +
                                   "END;";
            stmt.execute(dropDeptTable);

            String dropEmpTable = "BEGIN " +
                                  "EXECUTE IMMEDIATE 'DROP TABLE Employee'; " +
                                  "EXCEPTION WHEN OTHERS THEN NULL; " +
                                  "END;";
            stmt.execute(dropEmpTable);

            // 4. Create Tables
            String createDeptTable = "CREATE TABLE Department (Did NUMBER PRIMARY KEY, Dname VARCHAR2(100))";
            stmt.execute(createDeptTable);
            
            String createEmpTable = "CREATE TABLE Employee (Eid NUMBER PRIMARY KEY, Ename VARCHAR2(100), Salary NUMBER, Address VARCHAR2(100), Did NUMBER, FOREIGN KEY (Did) REFERENCES Department(Did))";
            stmt.execute(createEmpTable);

            // 5. Insert Data (Using individual INSERT statements)
            String insertDept1 = "INSERT INTO Department (Did, Dname) VALUES (1, 'HR')";
            String insertDept2 = "INSERT INTO Department (Did, Dname) VALUES (2, 'Finance')";
            String insertDept3 = "INSERT INTO Department (Did, Dname) VALUES (3, 'Engineering')";
            String insertDept4 = "INSERT INTO Department (Did, Dname) VALUES (4, 'Marketing')";
            String insertDept5 = "INSERT INTO Department (Did, Dname) VALUES (5, 'Sales')";
            
            stmt.executeUpdate(insertDept1);
            stmt.executeUpdate(insertDept2);
            stmt.executeUpdate(insertDept3);
            stmt.executeUpdate(insertDept4);
            stmt.executeUpdate(insertDept5);
            
            String insertEmp1 = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (101, 'John Doe', 50000, 'New York', 1)";
            String insertEmp2 = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (102, 'Jane Doe', 60000, 'California', 2)";
            String insertEmp3 = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (103, 'Sam Wilson', 70000, 'Texas', 3)";
            String insertEmp4 = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (104, 'Lisa Wong', 55000, 'Florida', 4)";
            String insertEmp5 = "INSERT INTO Employee (Eid, Ename, Salary, Address, Did) VALUES (105, 'Mike Johnson', 62000, 'Nevada', 5)";

            stmt.executeUpdate(insertEmp1);
            stmt.executeUpdate(insertEmp2);
            stmt.executeUpdate(insertEmp3);
            stmt.executeUpdate(insertEmp4);
            stmt.executeUpdate(insertEmp5);

            // 6. Read Data (Select)
            String selectQuery = "SELECT * FROM Employee";
            rs = stmt.executeQuery(selectQuery);
            
            System.out.println("Employee Data:");
            while (rs.next()) {
                System.out.println(rs.getInt("Eid") + " | " + rs.getString("Ename") + " | " + rs.getDouble("Salary") + " | " + rs.getString("Address") + " | " + rs.getInt("Did"));
            }

            // 7. Update Data (Update)
            String updateQuery = "UPDATE Employee SET Salary = ? WHERE Eid = ?";
            pstmt = conn.prepareStatement(updateQuery);
            pstmt.setDouble(1, 75000); // New salary
            pstmt.setInt(2, 103); // Employee ID to update
            int rowsUpdated = pstmt.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);

            // 8. Read Data after Update
            rs = stmt.executeQuery(selectQuery);
            System.out.println("Employee Data after Update:");
            while (rs.next()) {
                System.out.println(rs.getInt("Eid") + " | " + rs.getString("Ename") + " | " + rs.getDouble("Salary") + " | " + rs.getString("Address") + " | " + rs.getInt("Did"));
            }

            // 9. Delete Data (Delete)
            String deleteQuery = "DELETE FROM Employee WHERE Eid = ?";
            pstmt = conn.prepareStatement(deleteQuery);
            pstmt.setInt(1, 105); // Employee ID to delete
            int rowsDeleted = pstmt.executeUpdate();
            System.out.println("Rows deleted: " + rowsDeleted);

            // 10. Read Data after Delete
            rs = stmt.executeQuery(selectQuery);
            System.out.println("Employee Data after Delete:");
            while (rs.next()) {
                System.out.println(rs.getInt("Eid") + " | " + rs.getString("Ename") + " | " + rs.getDouble("Salary") + " | " + rs.getString("Address") + " | " + rs.getInt("Did"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 11. Clean up resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

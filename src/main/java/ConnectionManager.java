import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
    private static String url = "jdbc:mysql://localhost/mydb";
    private static String driverName = "com.mysql.jdbc.Driver";
    private static String username = "sa";
    private static String password = "";
    private static Connection con;
    private static String urlstring;

    public static Connection getConnection() {
        try {
            Class.forName(driverName);
            try {
                con = DriverManager.getConnection(urlstring, username, password);
            } catch (SQLException ex) {
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }

        return con;
    }
}

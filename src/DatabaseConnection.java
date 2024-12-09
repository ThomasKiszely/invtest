import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/invtest";

    private static final String USER = "root";

    private static final String PASSWORD = "ThomasRoot";

    public static Connection getconnection(){
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

import java.sql.Connection;
import java.sql.DriverManager;

public class db {

    public static Connection mycon() {
        String url = "jdbc:mysql://localhost:3306/mydb";
        String user = "root";
        String password = "tbqqqRrYVzvhjrU3fYN^";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            return con;
        } catch (Exception e) {
            e.printStackTrace();  // ✅ good practice: show the error
        }

        return null; // if connection failed
    }
}

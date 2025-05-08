import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //Notification n = new Notification(1, "System maintenance at 10 PM.");
        //n.saveToDatabase();
        //Notification n2 = new Notification(100, "Notification Test");
        //n2.saveToDatabase();

        //This code will clear the Users table before we upload a user.
        //This code is only for test purposes.
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "12345678");
            Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("TRUNCATE TABLE users");
            System.out.println("Users table cleared.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        User u = new User("Yiğit Kaan", "Önder", "kaan@example.com", "pass123");
        u.saveToDatabase();
        User u2 = new User("Hayati", "Kocur", "hayati@example.com", "pass123");
        u2.saveToDatabase();
    }
}

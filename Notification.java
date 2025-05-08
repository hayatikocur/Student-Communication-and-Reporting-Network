import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Notification {
    private int notificationId;
    private String notifContent;

    public Notification(String content) {
        this.notifContent = content;
    }

    public void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb"; // Replace with your DB name
        String user = "root";
        String password = "12345678";

        String sql = "INSERT INTO notifications (notif_content) VALUES (?)";

        System.out.println("Loading MySQL driver...");

        System.out.println("Connection URL: jdbc:mysql://localhost:3306/mydb");

        try (Connection conn = DriverManager.getConnection(url, user, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, this.notifContent);
            stmt.executeUpdate();
            System.out.println("Notification saved.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
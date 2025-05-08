/*
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Notification {
    private int notificationId;
    private String notifContent;

    public Notification(int id, String content) {
        this.notificationId = id;
        this.notifContent = content;
    }

    public void saveToDatabase() {
        String url = "jdbc:mysql://localhost:3306/mydb"; // Replace with your DB name
        String user = "root";
        String password = "your_mysql_password";

        String sql = "INSERT INTO notifications (notification_id, notif_content) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, this.notificationId);
            stmt.setString(2, this.notifContent);
            stmt.executeUpdate();
            System.out.println("Notification saved.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}*/

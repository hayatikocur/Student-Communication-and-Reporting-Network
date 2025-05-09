import java.io.File;
import java.io.FileInputStream;
import java.sql.*;

public class ImageUploader {
    public static void main(String[] args) {
        String url = "jdbc:mysql://139.179.224.206:3306/mydb";
        String user = "hayati";
        String password = "hayati123";

        String filePath = "rubberduck.jpg";

        String sql = "INSERT INTO media_attachments (image_name, mime_type, image_data) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             FileInputStream fis = new FileInputStream(new File(filePath))) {

            File imageFile = new File(filePath);
            stmt.setString(1, imageFile.getName());
            stmt.setString(2, "image/jpeg"); // You can make this dynamic if needed
            stmt.setBinaryStream(3, fis, fis.available());
            stmt.executeUpdate();

            System.out.println("Image uploaded.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
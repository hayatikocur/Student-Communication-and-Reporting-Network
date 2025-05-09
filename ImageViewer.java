import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.*;
import javax.imageio.ImageIO;

public class ImageViewer {

    public static void main(String[] args) {
        String url = "jdbc:mysql://139.179.224.206:3306/mydb";
        String user = "hayati";
        String password = "hayati123";

        String sql = "SELECT image_name, image_data FROM media_attachments WHERE picture_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, 2);  // change to the ID of the image you want to view
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String imageName = rs.getString("image_name");
                InputStream is = rs.getBinaryStream("image_data");

                BufferedImage image = ImageIO.read(is);

                int width = 400;
                int height = 300;
                Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
                BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = resizedImage.createGraphics();
                g2d.drawImage(scaledImage, 0, 0, null);
                g2d.dispose();

                // Show in Swing window
                JFrame frame = new JFrame("Image Viewer - " + imageName);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(600, 400);

                JLabel label = new JLabel(new ImageIcon(resizedImage));
                label.setHorizontalAlignment(JLabel.CENTER);
                frame.add(label, BorderLayout.CENTER);

                frame.setVisible(true);
            } else {
                System.out.println("No image found with given ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
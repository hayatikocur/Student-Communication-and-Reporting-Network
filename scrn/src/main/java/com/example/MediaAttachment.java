package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;

public class MediaAttachment {
    private int postId;
    private String mimeType;
    private byte[] imageData;

    public MediaAttachment(String fileLocation, int postId) {
        this.postId = postId;

        File file = new File(fileLocation);

        try (FileInputStream fis = new FileInputStream(file)) {
            this.imageData = fis.readAllBytes();
            this.mimeType = detectMimeType(fileLocation);
            saveToDatabase();
        } catch (IOException e) {
            System.err.println("Failed to read image file:");
            e.printStackTrace();
        }
    }

    private String detectMimeType(String fileLocation) {
        String lower = fileLocation.toLowerCase();
        if (lower.endsWith(".png")) return "image/png";
        if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) return "image/jpeg";
        if (lower.endsWith(".gif")) return "image/gif";
        if (lower.endsWith(".bmp")) return "image/bmp";
        if (lower.endsWith(".webp")) return "image/webp";
        return "application/octet-stream"; // default fallback
    }

    private void saveToDatabase() {
        String sql = "INSERT INTO media_attachments (postId, mime_type, image_data) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, this.postId);
            stmt.setString(2, this.mimeType);
            stmt.setBytes(3, this.imageData);

            stmt.executeUpdate();

            System.out.println("Media attachment saved to database.");

        } catch (SQLException e) {
            System.err.println("Failed to save media attachment to database:");
            e.printStackTrace();
        }
    }

    public static byte[] loadImageDataById(int mediaAttachmentId) {
        String sql = "SELECT image_data FROM media_attachments WHERE mediaAttachmentId = ?";
        try (Connection conn = DriverManager.getConnection(DBConfig.url, DBConfig.user, DBConfig.password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, mediaAttachmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBytes("image_data");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
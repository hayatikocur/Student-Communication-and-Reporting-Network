package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import javax.imageio.ImageIO;

public class Main {
    public static void main(String[] args) {

        String filePath = "scrn/target/classes/com/example/homeIcon.png";

        int mediaAttachmentId = 1;

        try {
            MediaAttachment attachment = new MediaAttachment(filePath, 1);
            System.out.println("✅ MediaAttachment created and saved to database.");
        } catch (Exception e) {
            System.err.println("❌ Failed to create MediaAttachment:");
            e.printStackTrace();
        }


        byte[] imageData = MediaAttachment.loadImageDataById(mediaAttachmentId);
        if (imageData == null) {
            System.err.println("Image not found in DB.");
            return;
        }

        try {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageData));
            if (image == null) {
                throw new RuntimeException("Unsupported image format or corrupted data.");
            }

            // Setup JFrame
            JFrame frame = new JFrame("Media Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(image.getWidth(), image.getHeight());

            // Add image to label
            JLabel label = new JLabel(new ImageIcon(image));
            frame.getContentPane().add(label, BorderLayout.CENTER);

            frame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
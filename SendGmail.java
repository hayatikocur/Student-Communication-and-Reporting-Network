import java.util.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.io.UnsupportedEncodingException;

public class SendGmail {

    public static void sendEmail(String toEmail, String subject, String body) {
        final String fromEmail = "06mert.mustafa13.mumcu@gmail.com"; // Gönderen Gmail
        final String password = "sgjfphwswqibtwnt"; // App password buraya

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(fromEmail, "Student Communication and Network System"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("E-posta başarıyla gönderildi.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<>();

        names.add("mert.mumcu@ug.bilkent.edu.tr");
        // names.add("yigitkaanonder@ug.bilkent.edu.tr");
        // names.add("hayati.kocur@ug.bilkent.edu.tr");
        // names.add("burhan.bulut@ug.bilkent.edu.tr");
        // names.add("emir.akar@ug.bilkent.edu.tr");

        for (int i = 0; i < names.size(); i++) {
            sendEmail(
                names.get(i),
                "Sorununuz Çözüldü!",
                "B-202'deki Bozuk Bilgisayarlar Tamir Edildi. Detaylı Bilgi İçin..."
            );
        }
    }
}
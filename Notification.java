
public class Notification {
    private int notificationId;
    private String notifContent;

    public Notification(int notificationId, String notifContent) {
        this.notificationId = notificationId;
        this.notifContent = notifContent;
    }

    public int getNotificationId() { return notificationId; }
    public String getNotifContent() { return notifContent; }
}

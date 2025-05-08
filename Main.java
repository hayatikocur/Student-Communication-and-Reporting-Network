public class Main {
    public static void main(String[] args) {
        Notification n = new Notification(1, "System maintenance at 10 PM.");
        n.saveToDatabase();
    }
}

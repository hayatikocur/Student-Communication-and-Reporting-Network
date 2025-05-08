import java.util.*;

public class Student extends User {
    public Student(int userId, String name, String surname, String email, String password) {
        super(name, surname, email, password);
    }
    private ArrayList<ProblemReport> savedReports = new ArrayList<>();
    private Set<ProblemReport> upvotes = new HashSet<>();
    private Set<ProblemReport> downvotes = new HashSet<>();
    private Set<Comment> likes = new HashSet<>();
    private Map<ProblemReport, Boolean> wasPostHelpful = new HashMap<>();

    public void incrementUpvotes(ProblemReport pr) {
        upvotes.add(pr);
        pr.incrementUpvoteCount();
    }

    public void incrementDownvotes(ProblemReport pr) {
        downvotes.add(pr);
        pr.incrementDownvoteCount();
    }

    public void incrementComments(ProblemReport pr) {
        pr.incrementCommentNumber();
    }

    public void attachMedia(ProblemReport pr, MediaAttachment attachment) {
        pr.addMediaAttachment(attachment);
    }

    public void saveReport(ProblemReport pr) {
        savedReports.add(pr);
    }

    public void unsaveReport(ProblemReport pr) {
        savedReports.remove(pr);
    }

    public void createReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
        ProblemReport report = new ProblemReport(title, description, category, location, attachment);
        // Add report to global list or DB
    }
}
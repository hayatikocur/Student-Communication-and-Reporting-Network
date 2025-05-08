/*import java.util.*;

public class Student extends User {
    private ArrayList<ProblemReport> savedReports;
    private Set<ProblemReport> upvotes;
    private Set<ProblemReport> downvotes;
    private Set<Comment> likes;
    private Map<ProblemReport, Boolean> wasPostHelpful;

    public Student(int userId, String userName, String userSurname, String email, String password) {
        super(userId, userName, userSurname, email, password);
        this.savedReports = new ArrayList<>();
        this.upvotes = new HashSet<>();
        this.downvotes = new HashSet<>();
        this.likes = new HashSet<>();
        this.wasPostHelpful = new HashMap<>();
    }

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
        if (!savedReports.contains(pr)) {
            savedReports.add(pr);
        }
    }

    public void unsaveReport(ProblemReport pr) {
        savedReports.remove(pr);
    }

    public void createReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
        ProblemReport report = new ProblemReport(title, description, category, location, attachment);
        // Add report to global list or DB
    }
}*/

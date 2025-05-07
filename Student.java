import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Student extends User {

    private ArrayList<ProblemReport> savedReports;
    private HashSet<ProblemReport> upvotes;
    private HashSet<ProblemReport> downvotes;
    private HashSet<Comment> likes;
    private HashMap<ProblemReport, Boolean> wasPostHelpful;

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
        ProblemReport pr = new ProblemReport(title, description, category, location, attachment);
        //  this will be added to the system-wide database or report list
    }

    //  add getters and setters as needed
}

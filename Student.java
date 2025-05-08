
import java.util.*;

public class Student extends User {
    private List<ProblemReport> savedReports = new ArrayList<>();
    private Set<ProblemReport> upvotedReports = new HashSet<>();
    private Set<ProblemReport> downvotedReports = new HashSet<>();
    private Set<Comment> likedComments = new HashSet<>();
    private Map<ProblemReport, Boolean> helpfulness = new HashMap<>();

    public Student(int userId, String userName, String userSurname, String email, String password) {
        super(userId, userName, userSurname, email, password);
    }

    public void createReport(String title, String description, Category category, Location location, MediaAttachment media) {
        ProblemReport report = new ProblemReport(title, description, category, location);
        if (media != null) {
            report.addMediaAttachment(media);
        }
        // Save to DB...
    }

    public void saveReport(ProblemReport pr) {
        savedReports.add(pr);
    }

    public void unsaveReport(ProblemReport pr) {
        savedReports.remove(pr);
    }

    public void upvoteReport(ProblemReport pr) {
        upvotedReports.add(pr);
        pr.incrementUpvoteCount();
    }

    public void downvoteReport(ProblemReport pr) {
        downvotedReports.add(pr);
        pr.incrementDownvoteCount();
    }

    public void markHelpful(ProblemReport pr, boolean wasHelpful) {
        helpfulness.put(pr, wasHelpful);
        if (wasHelpful) pr.incrementUseful();
        else pr.incrementNotUseful();
    }

    @Override
    public String getUserType() {
        return "Student";
    }
}


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ProblemReport {
    private static int idCounter = 1;

    private int reportId;
    private String reportTitle;
    private String description;
    private List<Comment> comments;
    private int upvoteCount;
    private int downvoteCount;
    private List<MediaAttachment> mediaAttachments;
    private int wasUsefulCount;
    private int wasNotUsefulCount;
    private int commentNumber;
    private Location location;
    private Category category;
    private boolean isResolved;
    private boolean isArchived;
    private LocalDateTime createdAt;

    public ProblemReport(String reportTitle, String description, Category category, Location location) {
        this.reportId = idCounter++;
        this.reportTitle = reportTitle;
        this.description = description;
        this.category = category;
        this.location = location;
        this.comments = new ArrayList<>();
        this.mediaAttachments = new ArrayList<>();
        this.createdAt = LocalDateTime.now();
        this.isResolved = false;
        this.isArchived = false;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        commentNumber++;
    }

    public void incrementUpvoteCount() {
        upvoteCount++;
    }

    public void incrementDownvoteCount() {
        downvoteCount++;
    }

    public void incrementUseful() {
        wasUsefulCount++;
    }

    public void incrementNotUseful() {
        wasNotUsefulCount++;
    }

    public void addMediaAttachment(MediaAttachment attachment) {
        mediaAttachments.add(attachment);
    }

    public void setResolved(boolean resolved) {
        this.isResolved = resolved;
    }

    public void setArchived(boolean archived) {
        this.isArchived = archived;
    }

    public int getReportId() { return reportId; }
    public String getReportTitle() { return reportTitle; }
    public String getDescription() { return description; }
    public Category getCategory() { return category; }
    public Location getLocation() { return location; }
    public List<Comment> getComments() { return comments; }
    public int getUpvoteCount() { return upvoteCount; }
    public boolean isResolved() { return isResolved; }
}

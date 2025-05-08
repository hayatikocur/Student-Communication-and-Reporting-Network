import java.util.ArrayList;

public class ProblemReport {
    private int reportId;
    private String reportTitle;
    private String description;
    private ArrayList<Comment> comments;
    private int upvoteCount;
    private int downvoteCount;
    private ArrayList<MediaAttachment> mediaAttachments;
    private int wasUsefulCount;
    private int wasNotUsefulCount;
    private int commentNumber;
    private Location location;
    private Category category;

    // Constructor
    public ProblemReport(int reportId, String reportTitle, String description,
                         Location location, Category category) {
        this.reportId = reportId;
        this.reportTitle = reportTitle;
        this.description = description;
        this.comments = new ArrayList<>();
        this.upvoteCount = 0;
        this.downvoteCount = 0;
        this.mediaAttachments = new ArrayList<>();
        this.wasUsefulCount = 0;
        this.wasNotUsefulCount = 0;
        this.commentNumber = 0;
        this.location = location;
        this.category = category;
    }

    // Getters and setters
    public int getReportId() { return reportId; }
    public String getReportTitle() { return reportTitle; }
    public String getDescription() { return description; }
    public ArrayList<Comment> getComments() { return comments; }
    public int getUpvoteCount() { return upvoteCount; }
    public int getDownvoteCount() { return downvoteCount; }
    public int getWasUsefulCount() { return wasUsefulCount; }
    public int getWasNotUsefulCount() { return wasNotUsefulCount; }
    public int getCommentNumber() { return commentNumber; }
    public Location getLocation() { return location; }
    public Category getCategory() { return category; }

    public void addComment(Comment comment) {
        comments.add(comment);
        commentNumber++;
    }

    public void addMediaAttachment(MediaAttachment attachment) {
        mediaAttachments.add(attachment);
    }

    public void upvote() {
        upvoteCount++;
    }

    public void downvote() {
        downvoteCount++;
    }

    public void markUseful() {
        wasUsefulCount++;
    }

    public void markNotUseful() {
        wasNotUsefulCount++;
    }
}

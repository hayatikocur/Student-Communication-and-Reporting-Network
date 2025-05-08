
import java.time.LocalDateTime;

public class Comment {
    private static int idCounter = 1;

    private int commentId;
    private String commentContent;
    private LocalDateTime time;
    private int likeNumber;
    private User author;
    private ProblemReport belongingReport;

    public Comment(User author, String content, ProblemReport report) {
        this.commentId = idCounter++;
        this.author = author;
        this.commentContent = content;
        this.time = LocalDateTime.now();
        this.belongingReport = report;
        this.likeNumber = 0;
    }

    public void like() {
        likeNumber++;
    }

    public int getLikeNumber() { return likeNumber; }
    public String getCommentContent() { return commentContent; }
    public LocalDateTime getTime() { return time; }
    public User getAuthor() { return author; }
}

package com.example;

import java.util.*;

public class ProblemReport {
    private static int counter = 0;
    private int reportId;
    private String reportTitle;
    private String description;
    private ArrayList<Comment> comments = new ArrayList<>();
    private int upvoteCount = 0;
    private int downvoteCount = 0;
    private ArrayList<MediaAttachment> mediaAttachments = new ArrayList<>();
    private int wasUsefulCount = 0;
    private int wasNotUsefulCount = 0;
    private int commentNumber = 0;
    private Location location;
    private Category category;
    private boolean resolved = false;
    private ArrayList<User> savedUsers;

    /*
    reportId(int): PK, AI
     reportTitle(varchar(limit)):
     reportDescription(varchar()):
     reportTime(time):
     We will get comments by Problem report id (select * from ... where id=x)
     upvoteCount(int) (0 by default):
     downvoteCount(int) (0 by default):
     mediaAttachments: create a media attachment table, store report's id in there, pull media attachments by that id.
     wasUsefulCount(int) (0 by default):
     wasNotUsefulCount(int) (0 by default):
     commentNumber(int) (0 by default):
     location: same as media attachments (maybe store each location as int?)
     category: same as media attachments
     resolved (boolean) (false by default):

     delete savedUsers and create a table in database named saved_posts. Its mysql code will be somwthing like:
     CREATE TABLE saved_posts (
        user_id INT,
        post_id INT,
        saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (user_id, post_id),
        FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
        FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
    );
     */

    public ProblemReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
        this.reportId = ++counter;
        this.reportTitle = title;
        this.description = description;
        this.category = category;
        this.location = location;
        savedUsers = new ArrayList<>();
        if (attachment != null)
            mediaAttachments.add(attachment);
    }

    public void addComment(Comment c) {
        comments.add(c);
        commentNumber++;
    }

    public void incrementUpvoteCount() {
        upvoteCount++;
    }

    public void incrementDownvoteCount() {
        downvoteCount++;
    }

    public void addMediaAttachment(MediaAttachment m) {
        mediaAttachments.add(m);
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }
  
    public void incrementCommentNumber() {
      
    }

    public void incrementWasNotUsefulCount(){
        wasNotUsefulCount++;
    }

    public void incrementWasUsefulCount(){
        wasUsefulCount++;
    }

    public ArrayList<User> getSavedUsers(){
        return savedUsers;
    }
}

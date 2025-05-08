package com.example.tutorialfx;

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

    public ProblemReport(String title, String description, Category category, Location location, MediaAttachment attachment) {
        this.reportId = ++counter;
        this.reportTitle = title;
        this.description = description;
        this.category = category;
        this.location = location;
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
}

package com.example;

import java.time.LocalDateTime;

public class Comment {
    private static int counter = 0;
    private int commentId;
    private String commentContent;
    private LocalDateTime time;
    private int likeNumber;
    private User author;
    private ProblemReport belongingReport;

    /*
    commentId(int): PK, AI
    userId(int):
    reportId(int):
    commentContent(varchar):
    time(Time):
    likeNumber(int):
     */

    public Comment(User author, String content) {
        this.commentId = ++counter;
        this.author = author;
        this.commentContent = content;
        this.time = LocalDateTime.now();
        this.likeNumber = 0;
    }

    public void like() {
        likeNumber++;
    }
}
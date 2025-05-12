package com.example;

public class MediaAttachment {
    private String fileLocation;
    /*
    mediaAttachmentId(int): AI, PK
    postId(int): we will store post's id and pull them from the id.
    mime_type(varchar):
    image_data(blob):
    time(Time):
     */

    public MediaAttachment(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
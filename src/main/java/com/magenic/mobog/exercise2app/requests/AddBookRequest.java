package com.magenic.mobog.exercise2app.requests;

public class AddBookRequest {

    private Long authorId;
    private String title;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}

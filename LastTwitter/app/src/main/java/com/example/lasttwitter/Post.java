package com.example.lasttwitter;

public class Post {
    private String postId, userName, postDate, postContent;

    public Post(String postId, String userName, String postDate, String postContent) {
        this.postId = postId;
        this.userName = userName;
        this.postDate = postDate;
        this.postContent = postContent;
    }

    public Post(){}

    public String getPostId() {
        return postId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPostDate() {
        return postDate;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }
}

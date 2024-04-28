package com.webApplication.videoShare.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(nullable = false, unique = true)
    private String videoId;

    @JsonBackReference
    @ManyToMany()
    private List<User> commentedUser = new ArrayList<>();

    private List<Pair<Long, List<String>>> comments = new ArrayList<>();

    @JsonBackReference
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    private long viewCount = 0;
    private  long likeCount = 0;
    private long dislikeCount = 0;

    @JsonBackReference
    @ManyToMany()
    private List<User> likedUser = new ArrayList<>();

    @JsonBackReference
    @ManyToMany()
    private List<User> dislikedUser = new ArrayList<>();

    @Enumerated(EnumType.ORDINAL)
    private LikeOrDislike likeOrDislike;

    public Video(long id, String title, String url, String videoId, List<User> commentedUser, List<Pair<Long, List<String>>> comments, User user, long viewCount, long likeCount, long dislikeCount, List<User> likedUser, List<User> dislikedUser, LikeOrDislike likeOrDislike) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.videoId = videoId;
        this.commentedUser = commentedUser;
        this.comments = comments;
        this.user = user;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.likedUser = likedUser;
        this.dislikedUser = dislikedUser;
        this.likeOrDislike = likeOrDislike;
    }

    public Video(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public List<User> getCommentedUser() {
        return commentedUser;
    }

    public void setCommentedUser(List<User> commentedUser) {
        this.commentedUser = commentedUser;
    }

    public List<Pair<Long, List<String>>> getComments() {
        return comments;
    }

    public void setComments(List<Pair<Long, List<String>>> comments) {
        this.comments = comments;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getViewCount() {
        return viewCount;
    }

    public void setViewCount(long viewCount) {
        this.viewCount = viewCount;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public long getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(long dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public List<User> getLikedUser() {
        return likedUser;
    }

    public void setLikedUser(List<User> likedUser) {
        this.likedUser = likedUser;
    }

    public List<User> getDislikedUser() {
        return dislikedUser;
    }

    public void setDislikedUser(List<User> dislikedUser) {
        this.dislikedUser = dislikedUser;
    }

    public LikeOrDislike getLikeOrDislike() {
        return likeOrDislike;
    }

    public void setLikeOrDislike(LikeOrDislike likeOrDislike) {
        this.likeOrDislike = likeOrDislike;
    }
}

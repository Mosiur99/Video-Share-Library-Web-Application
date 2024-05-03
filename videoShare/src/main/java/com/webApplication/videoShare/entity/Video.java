package com.webApplication.videoShare.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @JsonManagedReference
    @OneToMany(
            mappedBy = "video",
            cascade = CascadeType.ALL
    )
    private List<Comment> comments;

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

}

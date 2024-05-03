package com.webApplication.videoShare.dto;

import com.webApplication.videoShare.entity.Comment;
import com.webApplication.videoShare.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    private Long likeCount;
    private Long dislikeCount;
    private List<User> likedUsers = new ArrayList<>();
    private List<User> dislikedUsers = new ArrayList<>();
    private List<Comment> comments = new ArrayList<>();
}

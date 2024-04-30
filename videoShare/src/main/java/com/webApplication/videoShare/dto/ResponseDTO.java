package com.webApplication.videoShare.dto;

import com.webApplication.videoShare.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO {

    Long likeCount;
    Long dislikeCount;
    private List<User> likedUsers = new ArrayList<>();
    private List<User> dislikedUsers = new ArrayList<>();
}

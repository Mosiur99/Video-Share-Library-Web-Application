package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class VideoController {

    private final UserService userService;
    private final VideoService videoService;
    private final VideoRepository videoRepository;

    @Autowired
    public VideoController(UserService userService,
                           VideoService videoService,
                           VideoRepository videoRepository) {

        this.userService = userService;
        this.videoService = videoService;
        this.videoRepository = videoRepository;
    }

    @PostMapping("/user/addVideo")
    public String addNewVideo(@RequestParam String title,
                              @RequestParam String url) {
        videoService.newVideoAdded(title, url, userService.fetchUserId());
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/addVideo")
    public String addVideo(Model model) {
        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute("user", user);
        return "addVideo";
    }

    @PostMapping("/user/edit/{id}")
    public String editThisVideo(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String url) {
        videoService.updateVideo(id, title, url);
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/edit/{id}")
    public String editVideo(@PathVariable Long id,
                            Model model) {
        Video video = videoRepository.getReferenceById(id);
        model.addAttribute("video", video);
        return "editVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}/{id}")
    public String videoDetails(@PathVariable Long id,
                               Model model) {
        Video video = videoService.singleVideoDetails(id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(id);
        return "videoDetails";
    }

    @PostMapping("/user/likeOrDislike/{id}/{LikeOrDislike}")
    @ResponseBody
    public ResponseEntity<List<Long>> updateLikeOrDislike(@PathVariable Long id,
                                                      @PathVariable(name = "LikeOrDislike", required = true)LikeOrDislike likeOrDislike) {
        List<Long> actionCount = videoService.updateLikeOrDisLikeCount(id, likeOrDislike);
        return ResponseEntity.ok(actionCount);
    }

    @PostMapping("/user/likedUsers/{videoId}")
    @ResponseBody
    public ResponseEntity<List<User>> likedUserList(@PathVariable String videoId) {
        List<User> userList = videoService.likedUsers(videoId);
        return ResponseEntity.ok(userList);
    }

    @PostMapping("/user/dislikedUsers/{videoId}")
    @ResponseBody
    public ResponseEntity<List<User>> dislikedUserList(@PathVariable String videoId) {
        List<User> userList = videoService.dislikedUsers(videoId);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/view/{id}")
    public String viewVideo(@PathVariable Long id,
                            Model model) {
        Video video = videoService.singleVideoDetails(id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(id);
        return "view";
    }

}

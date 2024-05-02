package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.dto.ResponseDTO;
import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        videoService.addNewVideo(title, url, userService.fetchUserId());
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/addVideo")
    public String addVideo(Model model) {
        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute("user", user);
        return "addVideo";
    }

    @PostMapping("/user/edit/{videoId}/{id}")
    public String editThisVideo(@PathVariable String videoId,
                                @PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String url) {
        videoService.updateVideo(videoId, id, title, url);
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/edit/{videoId}/{id}")
    public String editVideo(@PathVariable String videoId,
                            @PathVariable Long id,
                            Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        model.addAttribute("video", video);
        return "editVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}/{id}")
    public String videoDetails(@PathVariable String videoId,
                               @PathVariable Long id,
                               Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(videoId, id);
        return "videoDetails";
    }

    @PostMapping("/user/likeOrDislike/{videoId}/{id}/{LikeOrDislike}")
    public ResponseEntity<ResponseDTO> updateLikeOrDislike(@PathVariable String videoId,
                                                          @PathVariable Long id,
                                                          @PathVariable(name = "LikeOrDislike", required = true)LikeOrDislike likeOrDislike) {
        ResponseDTO responseDTO = videoService.updateLikeOrDisLikeCount(videoId, id, likeOrDislike);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user/details/{videoId}/{id}")
    public ResponseEntity<ResponseDTO> getDetails(@PathVariable String videoId, @PathVariable Long id) {
        ResponseDTO responseDTO = videoService.getDetails(videoId, id);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user/dislikedUsers/{videoId}/{id}")
    public ResponseEntity<List<User>> dislikedUserList(@PathVariable String videoId, @PathVariable Long id) {
        List<User> userList = videoService.dislikedUsers(videoId, id);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/view/{videoId}/{id}")
    public String viewVideo(@PathVariable String videoId,
                            @PathVariable Long id,
                            Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(videoId, id);
        return "view";
    }

}

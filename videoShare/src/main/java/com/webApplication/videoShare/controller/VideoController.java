package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.dto.ResponseDTO;
import com.webApplication.videoShare.entity.LikeOrDislike;
import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class VideoController {

    private final UserService userService;
    private final VideoService videoService;

    @Autowired
    public VideoController(UserService userService,
                           VideoService videoService) {

        this.userService = userService;
        this.videoService = videoService;
    }

    @PostMapping("/user/addVideo")
    public String addNewVideo(@RequestParam String title,
                              @RequestParam String url) {
        String previousVideoId = "";
        videoService.addNewVideo(userService.fetchUserId(), url, title, previousVideoId);
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
        videoService.addNewVideo(id, url, title, videoId);
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/edit/{videoId}/{id}")
    public String editVideo(@PathVariable String videoId,
                            @PathVariable Long id,
                            Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        User user = userService.singleUserDetails(id);
        model.addAttribute("video", video);
        model.addAttribute("user", user);
        return "editVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}/{id}")
    public String videoDetails(@PathVariable String videoId,
                               @PathVariable Long id,
                               Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(id, videoId);
        return "videoDetails";
    }

    @PostMapping("/user/likeOrDislike/{videoId}/{id}/{LikeOrDislike}")
    public ResponseEntity<ResponseDTO> updateLikeOrDislike(@PathVariable String videoId,
                                                          @PathVariable Long id,
                                                          @PathVariable(name = "LikeOrDislike", required = true)LikeOrDislike likeOrDislike) {
        ResponseDTO responseDTO = videoService.updateLikeOrDislike(id, videoId, likeOrDislike);
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/user/details/{videoId}/{id}")
    public ResponseEntity<ResponseDTO> getDetails(@PathVariable String videoId, @PathVariable Long id) {
        ResponseDTO responseDTO = videoService.getDetails(id, videoId);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/view/{videoId}/{id}")
    public String viewVideo(@PathVariable String videoId,
                            @PathVariable Long id,
                            Model model) {
        Video video = videoService.singleVideoDetails(videoId, id);
        model.addAttribute("video", video);
        videoService.viewCountUpdate(id, videoId);
        return "view";
    }
}

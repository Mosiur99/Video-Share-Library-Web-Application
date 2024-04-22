package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.UserServiceImplement;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    @Autowired
//    private UserService userService;

    @GetMapping("/userSignup")
    public String signupForm(){
        return "userSignup";
    }

    @PostMapping("/userSignup")
    public String signupSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return "signupSuccess";
    }

    @GetMapping("/userLogin")
    public String loginPage(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "userLogin";
        }

        return "redirect:/user/userDashboard";
    }

//    @PostMapping("/userLogin")
//    public String loginSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
//
//    }

    @GetMapping("/user/userDashboard")
    public String userDashboard(Model model) {
        List<Video> videoList = videoService.getAllVideosByUserId();
        model.addAttribute("videoList", videoList);
        return "userDashboard";
    }

    @GetMapping("/home")
    public String homePage(Model model){
        List<Video> videoList = videoService.getAllVideos();

        model.addAttribute("videoList", videoList);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "home";
        }

        return "redirect:/user/userHome";
    }

    @GetMapping("/user/userHome")
    public String userHomePage(Model model){
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);

        return "userHome";
    }


//    @GetMapping("/videourl")
//    public String getVideoUrl(Model model){
//        List<Object> videosUrl = videoRepository.getVideosUrl();
//        model.addAttribute("videosUrl", videosUrl);
//        return "videourl";
//    }

//    @PostMapping("/userDashboard")
//    public String VideoAddedPage(){
//        return "addVideo";
//    }

    @PostMapping("/user/addVideo")
    public String addNewVideo(@RequestParam String title, @RequestParam String url){
//        Video video = new Video();
//        video.setTitle(title);
//        video.setUrl(url);
//        String videoId = videoService.extractVideoId(url);
//        video.setVideoId(videoId);
//        videoRepository.save(video);
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        Long id = 0L;
//        List<User> userList = userRepository.findAll();
//        for(User user : userList){
//            if(user.getUsername().equals(username)){
//                id = user.getId();
//                break;
//            }
//        }
        videoService.newVideoAdded(title, url, userService.fetchUserId());
        return "redirect:/user/userDashboard";
    }

    @GetMapping("/user/addVideo")
    public String addVideo(Model model){
        User user = userService.singleUserDetails(userService.fetchUserId());
        model.addAttribute("user", user);
        return "addVideo";
    }

    @GetMapping("/user/videoDetails/{videoId}")
    public String videoDetails(@PathVariable String videoId, Model model){
        Video video = videoService.singleVideoDetails(videoId);
        model.addAttribute("video", video);
        return "videoDetails";
    }

    @PostMapping("/user/like/{videoId}")
    public String likeVideo(@PathVariable String videoId){
        videoService.incrementLikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
    }

    @PostMapping("/user/dislike/{videoId}")
    public String dislikeVideo(@PathVariable String videoId){
        videoService.incrementDislikeCount(videoId);
        return "redirect:/user/videoDetails/{videoId}";
    }
}

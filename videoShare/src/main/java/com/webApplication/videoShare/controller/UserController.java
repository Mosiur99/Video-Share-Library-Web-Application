package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.Entity.User;
import com.webApplication.videoShare.Entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private VideoService videoService;

    @GetMapping("/userSignup")
    public String signupForm(){
        return "userSignup";
    }

    @PostMapping("/userSignup")
    public String signupSubmit(@RequestParam String username, @RequestParam String email, @RequestParam String password){
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "signupSuccess";
    }

    @GetMapping("/userDashboard")
    public String userDashboard(Model model) {
        List<Video> videoList = videoService.getAllVideos();
        model.addAttribute("videoList", videoList);
        return "userDashboard";
    }

    @PostMapping("/userDashboard")
    public String VideoAddedPage(){
        return "addVideo";
    }

    @PostMapping("/addVideo")
    public String addNewVideo(@RequestParam String title, @RequestParam String url){
        Video video = new Video();
        video.setTitle(title);
        video.setUrl(url);
        videoRepository.save(video);
        return "videoAddedSuccess";
    }
}

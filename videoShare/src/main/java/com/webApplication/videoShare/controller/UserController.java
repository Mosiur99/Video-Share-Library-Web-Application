package com.webApplication.videoShare.controller;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.entity.Video;
import com.webApplication.videoShare.repository.UserRepository;
import com.webApplication.videoShare.repository.VideoRepository;
import com.webApplication.videoShare.service.UserService;
import com.webApplication.videoShare.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


    @PostMapping("/userLogin")
    public String loginSubmit(@RequestParam String username, @RequestParam String password){
        if(userService.validUser(username, password)){
            return "redirect:/user/userDashboard";
        }
        return "redirect:/userLogin?error";
    }


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

}

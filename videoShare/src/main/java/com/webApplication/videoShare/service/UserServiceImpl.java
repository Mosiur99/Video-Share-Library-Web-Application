package com.webApplication.videoShare.service;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.exception.ResourceNotFoundException;
import com.webApplication.videoShare.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User singleUserDetails(Long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user.get();
    }

    @Override
    public Long fetchUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String email = userDetails.getUsername();
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isEmpty()){
            throw new ResourceNotFoundException();
        }
        return user.get().getId();
    }

    @Override
    public Boolean isValidUser(String email,
                               String password) {
        if (Objects.isNull(email) || email.isEmpty() || Objects.isNull(password) || password.isEmpty()) {
            return false;
        }

        Optional<User> user = userRepository.findUserByEmail(email);
        return user.isPresent();
    }

    @Override
    @Transactional
    public void saveNewUser(String email,
                            String username,
                            String password) {
        User user = userRepository.duplicateMailCheck(email);
        if (!Objects.isNull(user)) {
            throw new ResourceNotFoundException(email);
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(newUser);
    }
}

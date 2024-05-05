package com.webApplication.videoShare.config;

import com.webApplication.videoShare.entity.User;
import com.webApplication.videoShare.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if(user.isPresent()){
            User userObj = user.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(userObj.getEmail())
                    .password(userObj.getPassword())
                    .roles(getRoles(userObj))
                    .build();
        }
        else{
            throw new UsernameNotFoundException(email);
        }
    }

    private String[] getRoles(User user) {
        if(user.getRole() == null){
            return new String[]{"USER"};
        }

        return user.getRole().split(",");
    }
}

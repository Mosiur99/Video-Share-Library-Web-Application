package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findUserByEmail(String username);
}

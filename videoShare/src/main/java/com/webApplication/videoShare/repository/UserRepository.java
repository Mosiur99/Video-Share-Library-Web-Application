package com.webApplication.videoShare.repository;

import com.webApplication.videoShare.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Transactional
    Optional<User> findUserByEmail(String username);

    @Query(value = "SELECT user FROM User user WHERE user.id = :userId")
    User fetchUserById(@Param("userId") Long userId);

    @Query(value = "SELECT user FROM User user WHERE user.email = :email")
    User duplicateMailCheck(@Param("email") String email);
}

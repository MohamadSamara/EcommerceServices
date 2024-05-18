package com.samara.user.service.repository;

import com.samara.user.service.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findUserByUsername(String username); // Order 2 : create repo and findUserByUsername (Or findUserByEmail in case the email is the username)
}
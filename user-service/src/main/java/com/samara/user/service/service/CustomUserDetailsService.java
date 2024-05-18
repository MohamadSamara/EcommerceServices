package com.samara.user.service.service;

import com.samara.user.service.bo.CustomUserDetails;
import com.samara.user.service.model.Role;
import com.samara.user.service.model.UserEntity;
import com.samara.user.service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository usersRepository) {
        this.userRepository = usersRepository;
    }


    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buildCustomUserDetailsOfUsername(username);
    }

    private CustomUserDetails buildCustomUserDetailsOfUsername(String username) {
        UserEntity user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Incorrect Username"));

        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .password(user.getPassword())
                .telephone(user.getTelephone())
                .createdAt(LocalDateTime.now())
                .role(Role.valueOf(user.getRole().name()))
                .build();
    }
}
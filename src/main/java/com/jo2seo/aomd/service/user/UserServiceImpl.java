package com.jo2seo.aomd.service.user;

import com.jo2seo.aomd.exception.AlreadyInUserException;
import com.jo2seo.aomd.security.SecurityUtil;
import com.jo2seo.aomd.domain.User;
import com.jo2seo.aomd.repository.user.UserRepository;
import com.jo2seo.aomd.domain.UserRole;
import com.jo2seo.aomd.controller.user.dto.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers() {
        return userRepository.getUsers();
    }

    @Override
    public void signup(SignupRequest signupRequest) {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new AlreadyInUserException("Email Duplicate");
        }
        userRepository.signup(new User(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getProfileImgUrl(),
                signupRequest.getNickname(),
                UserRole.USER
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public User getMyUser() {
        User user = SecurityUtil.getCurrentEmail()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new RuntimeException("Cannot find user by email"));
        return user;
    }

    @Override
    public void updateProfileImg(String savedProfileImgUrl) {
        User user = getMyUser();
        user.updateProfileImgUrl(savedProfileImgUrl);
    }
}

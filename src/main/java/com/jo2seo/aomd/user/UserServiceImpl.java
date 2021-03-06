package com.jo2seo.aomd.user;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.security.SecurityUtil;
import com.jo2seo.aomd.user.dto.request.SignupRequest;
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
    public void signup(SignupRequest signupRequest) throws BaseException {
        if (userRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
            throw new BaseException(BaseResponseStatus.POST_USERS_EXISTS_EMAIL);
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
    public User getMyUser() throws BaseException {
        User user = SecurityUtil.getCurrentEmail()
                .flatMap(userRepository::findByEmail)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUEST_ERROR));
        return user;
    }

    @Override
    public void updateProfileImg(String savedProfileImgUrl) throws BaseException {
        User user = getMyUser();
        user.updateProfileImgUrl(savedProfileImgUrl);
    }
}

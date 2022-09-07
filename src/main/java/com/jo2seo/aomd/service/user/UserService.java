package com.jo2seo.aomd.service.user;

import com.jo2seo.aomd.domain.User;
import com.jo2seo.aomd.controller.user.dto.SignupRequest;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void signup(SignupRequest signupRequest);

    User getMyUser();

    void updateProfileImg(String savedProfileImgUrl);
}

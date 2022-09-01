package com.jo2seo.aomd.service.user;

import com.jo2seo.aomd.exception.BaseException;
import com.jo2seo.aomd.domain.User;
import com.jo2seo.aomd.dto.request.SignupRequest;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void signup(SignupRequest signupRequest) throws BaseException;

    User getMyUser() throws BaseException;

    void updateProfileImg(String savedProfileImgUrl) throws BaseException;
}

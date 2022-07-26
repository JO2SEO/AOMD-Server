package com.jo2seo.aomd.user;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.user.dto.request.SignupRequest;

import java.util.List;

public interface UserService {
    List<User> getUsers();
    void signup(SignupRequest signupRequest) throws BaseException;

    User getMyUser() throws BaseException;

    void updateProfileImg(String savedProfileImgUrl) throws BaseException;
}

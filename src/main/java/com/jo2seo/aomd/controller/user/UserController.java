package com.jo2seo.aomd.controller.user;

import com.jo2seo.aomd.controller.BaseResponse;
import com.jo2seo.aomd.file.FileService;
import com.jo2seo.aomd.service.user.UserService;
import com.jo2seo.aomd.domain.User;
import com.jo2seo.aomd.controller.user.dto.SignupRequest;
import com.jo2seo.aomd.dto.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/user/all")
    public ResponseEntity<List<GetUserResponse>> getUsers() {
        List<User> users = userService.getUsers();
        List<GetUserResponse> res = users.stream()
                .map(user -> new GetUserResponse(user.getId(), user.getNickname()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(res, OK);
    }

    @PostMapping("/user/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid SignupRequest signupRequest) {
        userService.signup(signupRequest);
        return new ResponseEntity<>(OK);
    }

    @PostMapping("/user/profileImg")
    public void postProfileImg(@RequestBody @Valid MultipartFile file) throws IOException {
        String savedProfileImgUrl = fileService.saveProfileImage(file);
        userService.updateProfileImg(savedProfileImgUrl);
    }

    /**
     * 현재 자신의 정보
     */
    @GetMapping("/user")
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    public BaseResponse<GetUserResponse> getUser() {
        User user = userService.getMyUser();
        return new BaseResponse<>(new GetUserResponse(user.getId(), user.getNickname()));
    }
}

package com.jo2seo.aomd.user;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.file.FileService;
import com.jo2seo.aomd.user.dto.request.SignupRequest;
import com.jo2seo.aomd.user.dto.response.GetUserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final FileService fileService;

    @GetMapping("/user/all")
    public BaseResponse getUsers() {
        List<User> users = userService.getUsers();
        List<GetUserResponse> res = users.stream().map(user -> new GetUserResponse(user.getId(), user.getNickname())).toList();
        return new BaseResponse(res);
    }

    @PostMapping("/user/signup")
    public BaseResponse signup(@RequestBody @Valid SignupRequest signupRequest) {
        try {
            userService.signup(signupRequest);
        } catch (BaseException e) {
            return new BaseResponse(e.getStatus());
        }
        return new BaseResponse(BaseResponseStatus.SUCCESS);
    }

    @PostMapping("/user/profileImg")
    public void postProfileImg(@RequestBody @Valid MultipartFile file) throws IOException, BaseException {
        String savedProfileImgUrl = fileService.saveProfileImage(file);
        userService.updateProfileImg(savedProfileImgUrl);
    }

    /**
     * 현재 자신의 정보
     */
    @GetMapping("/user")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
    public BaseResponse getUser() {
        try {
            User user = userService.getMyUser();
            return new BaseResponse(new GetUserResponse(user.getId(), user.getNickname()));
        } catch (BaseException e){
            return new BaseResponse(e.getStatus());
        }
    }
}

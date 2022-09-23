package jo2seo.aomd.api.member;

import jo2seo.aomd.api.member.dto.SignupRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequestMapping("/api/v1")
public interface MemberController {

    @GetMapping("/user/all")
    ResponseEntity getMembers();
    
    @PostMapping("/user/signup")
    ResponseEntity signup(@RequestBody @Valid SignupRequest signupRequest);


    @PostMapping("/user/profileImg")
    void postProfileImg(@RequestBody @Valid MultipartFile file) throws IOException;

    /**
     * 현재 자신의 정보
     */
    @GetMapping("/user")
    @PreAuthorize(value = "hasAnyAuthority('USER', 'ADMIN')")
    ResponseEntity getMember();
}

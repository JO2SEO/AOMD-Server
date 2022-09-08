package jo2seo.aomd.controller.member;

import jo2seo.aomd.api.member.dto.GetUserResponse;
import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.file.FileService;
import jo2seo.aomd.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class MemberController implements jo2seo.aomd.api.member.MemberController {
    private final MemberService memberService;
    private final FileService fileService;

    @Override
    public ResponseEntity getMembers() {
        List<Member> members = memberService.findAllMembers();
        List<GetUserResponse> res = members.stream()
                .map(user -> new GetUserResponse(user.getId(), user.getNickname()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(res, OK);
    }

    @Override
    public ResponseEntity signup(SignupRequest signupRequest) {
        memberService.signup(signupRequest);
        return new ResponseEntity<>(OK);
    }

    @Override
    public void postProfileImg(MultipartFile file) throws IOException {
        String savedProfileImgUrl = fileService.saveProfileImage(file);
        memberService.updateProfileImg(savedProfileImgUrl);
    }

    /**
     * 현재 자신의 정보
     */
    @Override
    public ResponseEntity getMember() {
        Member member = memberService.getMyMember();
        return new ResponseEntity(new GetUserResponse(member.getId(), member.getNickname()), OK);
    }
}

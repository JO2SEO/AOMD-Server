package jo2seo.aomd.service.user;

import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.exception.AlreadyInUserException;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<Member> findAllMembers() {
        return memberRepository.findAll();
    }

    @Override
    public void signup(SignupRequest signupRequest) {
        if (memberRepository.findMemberByEmail(signupRequest.getEmail()).isPresent()) {
            throw new AlreadyInUserException("Email Duplicate");
        }
        memberRepository.save(new Member(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getProfileImgUrl(),
                signupRequest.getNickname(),
                UserRole.USER
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getMyMember() {
        Member member = SecurityUtil.getCurrentEmail()
                .flatMap(memberRepository::findMemberByEmail)
                .orElseThrow(() -> new RuntimeException("Cannot find member by email"));
        return member;
    }

    @Override
    public void updateProfileImg(String savedProfileImgUrl) {
        Member member = getMyMember();
        member.updateProfileImgUrl(savedProfileImgUrl);
    }
}

package jo2seo.aomd.service.member;

import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.exception.AlreadyInMemberException;
import jo2seo.aomd.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jo2seo.aomd.security.SecurityUtil.*;

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
            throw new AlreadyInMemberException();
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
        Member member = getCurrentEmail()
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

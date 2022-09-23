package jo2seo.aomd.service.member;

import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.exception.AlreadyInMemberException;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
    }

    @Override
    @Transactional
    public Member signup(SignupRequest signupRequest) {
        if (memberRepository.findByEmail(signupRequest.getEmail()).isPresent() ||
                memberRepository.findByNickname(signupRequest.getNickname()).isPresent()) {
            throw new AlreadyInMemberException();
        }
        return memberRepository.save(new Member(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getProfileImgUrl(),
                signupRequest.getNickname(),
                UserRole.USER
        ));
    }

    @Override
    @Transactional(readOnly = true)
    public Member getCurrentMember() {
        Member member = getCurrentEmail()
                .flatMap(memberRepository::findByEmail)
                .orElseThrow(MemberNotFoundException::new);
        return member;
    }

    @Override
    @Transactional
    public void updateProfileImg(String savedProfileImgUrl) {
        Member member = getCurrentMember();
        member.updateProfileImgUrl(savedProfileImgUrl);
    }
}

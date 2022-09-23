package jo2seo.aomd.service.member;

import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.exception.AlreadyInMemberException;
import jo2seo.aomd.repository.user.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MemberServiceTests {

    private static final Logger LOG = LoggerFactory.getLogger(MemberServiceTests.class);

    @Autowired
    private MemberService memberService;
    
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        createBulkMember();
    }
    
//    @AfterEach
//    void tearDown() {
//        deleteBulkMember();
//    }

    /**
     * 정상 회원가입 케이스
     */
    @Test
    void signupTest() {
        SignupRequest request = new SignupRequest("a@a.com", "pw", "url", "aa");

        memberService.signup(request);
        Member member = memberService.findByEmail(request.getEmail());

        assertEquals(request.getEmail(), member.getEmail());
        assertEquals(request.getNickname(), member.getNickname());
        assertEquals(request.getProfileImgUrl(), member.getProfileImgUrl());
    }

    /**
     * 회원가입 시 이메일이 중복될 때
     */
    @Test
    void signupDuplicateEmailErrorTest() {
        SignupRequest request = new SignupRequest("test1@test.com", "pw", "url", "aa");

        assertThrows(AlreadyInMemberException.class, () -> {
            memberService.signup(request);
        });
    }

    /**
     * 회원가입 시 닉네임이 중복될 때
     */
    @Test
    void signupDuplicateNicknameErrorTest() {
        SignupRequest request = new SignupRequest("a@a.com", "pw", "url", "member1");

        assertThrows(AlreadyInMemberException.class, () -> {
            memberService.signup(request);
        });
    }


    private void createBulkMember() {
        Member member1 = new Member("test1@test.com", "pwpw", "url", "member1", UserRole.USER);
        Member member2 = new Member("test2@test.com", "pwpw", "url", "member2", UserRole.USER);
        Member member3 = new Member("test3@test.com", "pwpw", "url", "member3", UserRole.USER);
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

    private void deleteBulkMember() {
        memberRepository.deleteAll();
    }
}

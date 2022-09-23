package jo2seo.aomd.repository.member;

import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.repository.user.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * DB에 아무도 없는데 findAll() 을 호출한 경우
     * return emptyList
     */
    @Test 
    void emptyListWhenEmptyMemberDB() {
        List<Member> all = memberRepository.findAll();
        assertEquals(all.size(), 0);
    }

    /**
     * 잘못된 Id 로 Member 를 찾는 경우
     */
    @Test
    void emptyOptionalWhenEmptyMemberDB() {
        assertThrows(NoSuchElementException.class, () -> {
            Optional<Member> none = memberRepository.findById(-1L);
            none.orElseThrow();
        });
    }

    /**
     * email 로 member 찾기
     */
    @Test
    void findMemberByEmailTest() {
        Member member = new Member("a@a.com", "pw", "url", "aa", UserRole.USER);
        memberRepository.save(member);

        Optional<Member> memberByEmail = memberRepository.findByEmail("a@a.com");

        assertEquals(memberByEmail.get().getId(), 1);
        assertEquals(memberByEmail.get().getEmail(), "a@a.com");
        assertEquals(memberByEmail.get().getNickname(), "aa");
    }
}

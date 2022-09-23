package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class PortfolioCreateServiceTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService; 

    @Autowired
    private PortfolioCRUDService portfolioService;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @BeforeEach
    void setUp() {
        createBulkMember();
    }

    /**
     * 임의의 member 가 title 만 가진 포트폴리오를 생성
     * member 에게 잘 매핑되는지 확인
     */
    @Test
    void createPortfolioTest() {
        // given
        CreatePortfolioRequest request = 
                new CreatePortfolioRequest("Test Portfolio");

        Member member = memberService.findByEmail("test1@test.com");

        // when
        Portfolio newPortfolio =
                portfolioService.createNewPortfolio("test1@test.com", request);

        List<Portfolio> memberPortfolioList = 
                portfolioRepository.findAllByMember(member);

        // then
        assertEquals(1, memberPortfolioList.size());
        assertEquals("Test Portfolio", newPortfolio.getTitle());
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

package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.exception.portfolio.NoSharingPortfolioException;
import jo2seo.aomd.exception.portfolio.NotMyPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
public class PortfolioReadServiceTests {

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PortfolioCRUDService portfolioService;

    private final static String TestUserEmail = "test@test.com";
    private final static String TestUserEmail2 = "test2@test.com";

    private final Map<String, Long> portfolioIdMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        initData();
    }
    
    /**
     * 유저 정보 확인 후 알맞은 portfolio 반환
     */
    @Test
    void checkMineAndGetTest() {
        portfolioIdMap.forEach((k, v) -> {
            Portfolio portfolio = portfolioService.checkMineAndGet(TestUserEmail, v);
            assertEquals(k, portfolio.getTitle());
        });
    }

    /**
     * 포트폴리오가 현재 유저의 것이 아닐 때 
     */
    @Test
    void checkNotMineAndGetTest() {
        portfolioIdMap.forEach((k, v) -> {
            assertThrows(NotMyPortfolioException.class, () -> {
                portfolioService.checkMineAndGet(TestUserEmail2, v);
            });
        });
    }

    /**
     * 현재 포트폴리오가 공유된 상태일 때 외부인이 열람
     */
    @Test
    @Transactional
    void checkSharingAndGetOthersTest() {
        portfolioIdMap.forEach((k, v) -> {
            Portfolio before = portfolioService.findById(v);
            before.updateSharing(true);
            portfolioRepository.save(before);
            
            Portfolio after = portfolioService.checkSharingAndGet(before.getShareUrl());
            assertEquals(before.getId(), after.getId());
            assertEquals(before.getTitle(), after.getTitle());
            assertNotEquals(before.getCreatedAt(), after.getUpdatedAt());
        });
    }

    /**
     * 현재 포트폴리오가 공유된 상태가 아닐 때 외부인이 열람할 수 없음
     */
    @Test
    void checkSharingAndCannotOthersTest() {
        portfolioIdMap.forEach((k, v) -> {
            assertThrows(NoSharingPortfolioException.class, () -> {
                Portfolio portfolio = portfolioService.findById(v);
                portfolioService.checkSharingAndGet(portfolio.getShareUrl());
            });
        });
    }
    

    private void initData() {
        Member member = new Member(TestUserEmail, "pw", "url", "nick", UserRole.USER);
        Member member2 = new Member(TestUserEmail2, "pw", "url", "nick2", UserRole.USER);
        memberRepository.save(member);
        memberRepository.save(member2);
        CreatePortfolioRequest request1 = new CreatePortfolioRequest("portfolio1");
        CreatePortfolioRequest request2 = new CreatePortfolioRequest("portfolio2");
        CreatePortfolioRequest request3 = new CreatePortfolioRequest("portfolio3");
        Portfolio newPortfolio1 = portfolioService.createNewPortfolio("test@test.com", request1);
        Portfolio newPortfolio2 = portfolioService.createNewPortfolio("test@test.com", request2);
        Portfolio newPortfolio3 = portfolioService.createNewPortfolio("test@test.com", request3);

        portfolioIdMap.put("portfolio1", newPortfolio1.getId());
        portfolioIdMap.put("portfolio2", newPortfolio2.getId());
        portfolioIdMap.put("portfolio3", newPortfolio3.getId());
    }
    
}

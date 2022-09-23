package jo2seo.aomd.service.portfolio.mapper;

import jo2seo.aomd.api.portfolio.dto.PortfolioDto;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled
@ActiveProfiles("test")
@SpringBootTest
public class PortfolioMapperTests {
    
    @Autowired
    private PortfolioMapper portfolioMapper;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Test
    public void portfolioDtoMapperTest() {

        assertNotNull(portfolioMapper);

        // given
        Member member = new Member("email@dd.com", "pw", "url", "nick", UserRole.ADMIN);
        memberRepository.save(member);
        Portfolio portfolio = new Portfolio(member, "title");
        portfolioRepository.save(portfolio);
        
        // when
        PortfolioDto portfolioDto = portfolioMapper.entityToDTO(portfolio);

        //then
        assertEquals(portfolioDto.getTitle(), portfolio.getTitle());
        assertEquals(portfolioDto.getPortfolioId(), portfolio.getId());
        assertEquals(portfolioDto.getMemberId(), member.getId());
        assertEquals(portfolioDto.getShareUrl(), portfolio.getShareUrl());
        assertEquals(portfolioDto.getSharing(), portfolio.getSharing());
    }
}

package jo2seo.aomd.service.portfolio;


import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.ForbiddenException;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import jo2seo.aomd.service.portfolio.validation.PortfolioValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioCreateServiceImpl implements PortfolioCreateService {
    
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    private final PortfolioValidator portfolioValidator;
    

    @Override
    public Portfolio createNewPortfolio(CreatePortfolioRequest createPortfolioRequest) {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Member member = memberRepository.findMemberByEmail(userEmail)
                .orElseThrow(MemberNotFoundException::new);

        String title = createPortfolioRequest.getTitle();
        Portfolio portfolio = new Portfolio(member, title);
        
        portfolioRepository.save(portfolio);
        return portfolio;
    }
    

    @Override
    public Portfolio addBlock(String shareUrl, String blockId) {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolioValidator.checkMine(userEmail, portfolio);
        
        /* TODO: blockId에 해당하는 블록이 자신의 것이 아니면 exception */
        /* 블록체인과 연결이 구현되면 가능함 */
        

        portfolio.addNewBlock(blockId);
        return portfolio;
    }
}

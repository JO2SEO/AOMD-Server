package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioUpdateServiceImpl implements PortfolioUpdateService{

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public Portfolio update(String shareUrl, UpdatePortfolioRequest updatePortfolioRequest) {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        
        Member member = memberRepository.findMemberByEmail(userEmail)
                .orElseThrow(MemberNotFoundException::new);

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(member, shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.updateAll(updatePortfolioRequest);
        
        return portfolio;
    }
}

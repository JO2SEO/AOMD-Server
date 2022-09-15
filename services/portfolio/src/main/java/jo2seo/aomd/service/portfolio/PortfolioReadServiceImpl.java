package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.GetAllPortfolioTitle;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import jo2seo.aomd.service.portfolio.validation.PortfolioValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class PortfolioReadServiceImpl implements PortfolioReadService {
    
    private final PortfolioRepository portfolioRepository;
    private final PortfolioValidator portfolioValidator;
    private final MemberRepository memberRepository;

    @Override
    public List<GetAllPortfolioTitle> findAll() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        List<Portfolio> allByMember = portfolioRepository.findAllByMember(member);
        return allByMember.stream().map(portfolio -> new GetAllPortfolioTitle(portfolio.getShareUrl(), portfolio.getTitle())).collect(Collectors.toList());
    }

    @Override
    public Portfolio checkMineAndGet(String userEmail, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findOneById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkMine(userEmail, portfolio);
        return portfolio;
    }

    @Override
    public Portfolio checkSharingAndGet(String shareUrl) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkSharing(portfolio);
        return portfolio;
    }

    @Override
    public Portfolio findByUrl(String shareUrl) {
        return portfolioRepository.findOneByUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
    }

    @Override
    public Portfolio findById(Long portfolioId) {
        return portfolioRepository.findOneById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
    }
}

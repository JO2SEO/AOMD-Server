package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.api.portfolio.dto.GetAllPortfolioTitle;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import jo2seo.aomd.service.portfolio.validation.PortfolioValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PortfolioCRUDServiceImpl implements PortfolioCRUDService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final PortfolioValidator portfolioValidator;

    @Override
    public List<GetAllPortfolioTitle> findAll() {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Member member = memberRepository.findMemberByEmail(userEmail)
                .orElseThrow(NoMatchingPortfolioException::new);

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


    @Override
    @Transactional
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
    @Transactional
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


    @Override
    @Transactional
    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.removeBlock(blockId);
    }
    
}

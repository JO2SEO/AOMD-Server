package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.api.portfolio.dto.PortfolioTitleDto;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.service.portfolio.validation.PortfolioValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static jo2seo.aomd.security.SecurityUtil.getCurrentEmail;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioCRUDServiceImpl implements PortfolioCRUDService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final PortfolioValidator portfolioValidator;

    @Override
    public List<Portfolio> findAllPortfolioByMember(String memberEmail) {

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(NoMatchingPortfolioException::new);

        return portfolioRepository.findAllByMember(member);
    }
    

    /**
     * 자신이 가진 포트폴리오의 제목만 받는다.
     * @return
     */
    @Override
    public List<PortfolioTitleDto> findSimplePortfolioAllByMember(String memberEmail) {
        
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(NoMatchingPortfolioException::new);
        
        return portfolioRepository.findAllByMember(member).stream()
                .map(Portfolio::createOnlyTitleDto)
                .collect(Collectors.toList());
    }

    @Override
    public Portfolio checkMineAndGet(String memberEmail, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkMine(memberEmail, portfolio);
        
        return portfolio;
    }

    @Override
    public Portfolio checkSharingAndGet(String shareUrl) {
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkSharing(portfolio);
        
        return portfolio;
    }

    @Override
    public Portfolio findByUrl(String shareUrl) {
        return portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
    }

    @Override
    public Portfolio findById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
    }
    
    @Override
    @Transactional
    public Portfolio createNewPortfolio(String memberEmail, CreatePortfolioRequest createPortfolioRequest) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        String title = createPortfolioRequest.getTitle();
        Portfolio portfolio = new Portfolio(member, title);

        portfolioRepository.save(portfolio);
        return portfolio;
    }

    @Override
    @Transactional
    public Portfolio update(String memberEmail, String shareUrl, UpdatePortfolioRequest updatePortfolioRequest) {

        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        Portfolio portfolio = portfolioRepository.findByMemberAndShareUrl(member, shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.updateAll(updatePortfolioRequest);

        return portfolio;
    }

    @Override
    @Transactional
    public Portfolio addBlock(String shareUrl, String blockId) {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolioValidator.checkMine(memberEmail, portfolio);

        /* TODO: blockId에 해당하는 블록이 자신의 것이 아니면 exception */
        /* 블록체인과 연결이 구현되면 가능함 */


        portfolio.addNewBlock(blockId);
        return portfolio;
    }
    
    @Override
    @Transactional
    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.removeBlock(blockId);
    }
}

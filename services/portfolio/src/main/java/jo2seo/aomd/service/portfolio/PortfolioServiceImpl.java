package jo2seo.aomd.service.portfolio;


import jo2seo.aomd.api.portfolio.dto.FindOneByShareUrlResponse;
import jo2seo.aomd.api.portfolio.dto.GetAllPortfolioTitle;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.PortfolioBlockOrder;
import jo2seo.aomd.exception.ForbiddenException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.security.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioServiceImpl implements PortfolioService{
    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<GetAllPortfolioTitle> getAll() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        List<Portfolio> allByMember = portfolioRepository.findAllByMember(member);
        return allByMember.stream().map(portfolio -> new GetAllPortfolioTitle(portfolio.getShareUrl(), portfolio.getTitle())).collect(Collectors.toList());
    }


    @Override
    public boolean checkMine(String shareUrl) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        return portfolioRepository.checkIsMine(shareUrl, member);
    }

    @Override
    public boolean checkSharing(String shareUrl) {
        return portfolioRepository.checkSharing(shareUrl);
    }

    @Override
    public String findIdByShareUrl(String shareUrl) {
        return portfolioRepository.findIdByUrl(shareUrl).orElseThrow();
    }

    @Override
    public FindOneByShareUrlResponse findOneByShareUrl(String shareUrl, boolean isMine) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        List<PortfolioBlockOrder> portfolioBlockOrderList = portfolio.getPortfolioBlockOrderList();
        return new FindOneByShareUrlResponse(
                !isMine,
                portfolio.getTitle(),
                portfolio.getSharing(),
                portfolio.getCreatedAt(),
                portfolio.getUpdatedAt(),
                portfolioBlockOrderList.stream().map(PortfolioBlockOrder::getBlockId).collect(Collectors.toList())
        );
    }

    @Override
    public Portfolio createNewPortfolio() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        String title = "new Portfolio";

        Portfolio portfolio = new Portfolio(member, title);
        portfolioRepository.save(portfolio);
        return portfolio;
    }

    @Override
    public void updateTitle(String shareUrl, String title) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(member, shareUrl).orElseThrow();
        portfolio.updateTitle(title);
    }

    @Override
    public void updateOrder(String shareUrl, List<String> blockIdList) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(member, shareUrl).orElseThrow();
        portfolio.updateOrder(blockIdList);
    }

    @Override
    public void updateSharing(String shareUrl, boolean sharing) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(member, shareUrl).orElseThrow();
        portfolio.updateSharing(sharing);
    }

    @Override
    public boolean addBlock(String shareUrl, String blockId) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        Member member = memberRepository.findMemberByEmail(userEmail).orElseThrow();
        /* shareUrl에 해당하는 포트폴리오가 자신의 것이 아니면 exception */
        boolean isMine = portfolioRepository.checkIsMine(shareUrl, member);
        if (!isMine) throw new ForbiddenException();

        /* TODO: blockId에 해당하는 블록이 자신의 것이 아니면 exception */
        /* 블록체인과 연결이 구현되면 가능함 */




        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        /* 이미 추가된 block이면 throw exception */
        if (portfolio.blockExists(blockId)) {
            return false;
        }

        portfolio.addNewBlock(blockId);
        return true;
    }

    @Override
    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        portfolio.removeBlock(blockId);
    }
}

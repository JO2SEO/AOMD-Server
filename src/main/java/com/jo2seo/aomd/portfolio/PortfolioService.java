package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.portfolio.dto.FindOneByShareUrlResponse;
import com.jo2seo.aomd.portfolio.dto.GetAllResponse;
import com.jo2seo.aomd.security.SecurityUtil;
import com.jo2seo.aomd.user.User;
import com.jo2seo.aomd.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<GetAllResponse> getAll() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        List<Portfolio> allByUser = portfolioRepository.findAllByUser(user);
        return allByUser.stream().map(portfolio -> new GetAllResponse(portfolio.getShareUrl(), portfolio.getTitle())).collect(Collectors.toList());
    }

    /*
    case 1) URL에 해당하는 포트폴리오가 자기 것
    그냥 정보 다 내려줌
    case 2) URL에 해당하는 포트폴리오가 남의 것
    case 2-1) shared가 true면 정보 다 내려줌(title 제외하고)
    case 2-2) shared가 false면 isShared를 false로 하고 아무것도 안 내려줌
    */
    @Transactional(readOnly = true)
    public FindOneByShareUrlResponse findOneByShareUrl(String shareUrl) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        boolean isMine = portfolioRepository.checkIsMine(shareUrl, user);
        boolean isShared = portfolioRepository.checkSharing(shareUrl);

        FindOneByShareUrlResponse res;
        if (isMine) {
            Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
            List<PortfolioChainOrder> portfolioChainOrderList = portfolio.getPortfolioChainOrderList();
            portfolioChainOrderList.sort(Comparator.comparing(PortfolioChainOrder::getOrderIndex));
            res = new FindOneByShareUrlResponse(
                    portfolio.getTitle(),
                    portfolio.getSharing(),
                    portfolio.getCreatedAt(),
                    portfolio.getUpdatedAt(),
                    portfolioChainOrderList.stream().map(PortfolioChainOrder::getChainId).collect(Collectors.toList())
            );
        } else {
            if (isShared) {
                Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
                List<PortfolioChainOrder> portfolioChainOrderList = portfolio.getPortfolioChainOrderList();
                portfolioChainOrderList.sort(Comparator.comparing(PortfolioChainOrder::getOrderIndex));
                res =  new FindOneByShareUrlResponse(
                        null,
                        portfolio.getSharing(),
                        portfolio.getCreatedAt(),
                        portfolio.getUpdatedAt(),
                        portfolioChainOrderList.stream().map(PortfolioChainOrder::getChainId).collect(Collectors.toList())
                );
            } else {
                res = new FindOneByShareUrlResponse (
                        null,
                        false,
                        null,
                        null,
                        null
                );
            }
        }
        return res;
    }

    public String createNewPortfolio() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        String title = "new Portfolio";

        Portfolio portfolio = new Portfolio(user, title);
        portfolioRepository.save(portfolio);
        return portfolio.getShareUrl();
    }

    public void updateTitle(String shareUrl, String title) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateTitle(title);
    }

    public void updateOrder(String shareUrl, List<String> chainIdList) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateOrder(chainIdList);
    }

    public void updateShared(String shareUrl, boolean shared) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateShared(shared);
    }

    public void addBlock(String shareUrl, String blockId) throws BaseException {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        /* shareUrl에 해당하는 포트폴리오가 자신의 것이 아니면 exception */
        boolean isMine = portfolioRepository.checkIsMine(shareUrl, user);
        if (!isMine) {
            throw new BaseException(BaseResponseStatus.INVALID_USER_JWT);
        }

        /* TODO: blockId에 해당하는 블록이 자신의 것이 아니면 exception */
        /* 블록체인과 연결이 구현되면 가능함 */

        log.info("정상적으로 portfolio에 block을 추가함");

        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        PortfolioChainOrder portfolioChainOrder = new PortfolioChainOrder(portfolio, blockId);
        portfolioRepository.savePortfolioBlock(portfolioChainOrder);
        portfolio.addNewBlock(portfolioChainOrder);
    }
}

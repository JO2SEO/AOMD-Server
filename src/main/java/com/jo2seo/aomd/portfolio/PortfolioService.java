package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.exception.ForbiddenException;
import com.jo2seo.aomd.portfolio.dto.response.FindOneByShareUrlResponse;
import com.jo2seo.aomd.portfolio.dto.response.GetAllResponse;
import com.jo2seo.aomd.security.SecurityUtil;
import com.jo2seo.aomd.user.User;
import com.jo2seo.aomd.user.UserRepository;
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

    @Transactional(readOnly = true)
    public boolean checkMine(String shareUrl) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        return portfolioRepository.checkIsMine(shareUrl, user);
    }

    @Transactional(readOnly = true)
    public boolean checkSharing(String shareUrl) {
        return portfolioRepository.checkSharing(shareUrl);
    }

    @Transactional(readOnly = true)
    public String findIdByShareUrl(String shareUrl) {
        return portfolioRepository.findIdByUrl(shareUrl).orElseThrow();
    }

    @Transactional(readOnly = true)
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

    public Portfolio createNewPortfolio() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        String title = "new Portfolio";

        Portfolio portfolio = new Portfolio(user, title);
        portfolioRepository.save(portfolio);
        return portfolio;
    }

    public void updateTitle(String shareUrl, String title) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateTitle(title);
    }

    public void updateOrder(String shareUrl, List<String> blockIdList) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateOrder(blockIdList);
    }

    public void updateSharing(String shareUrl, boolean sharing) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        Portfolio portfolio = portfolioRepository.findOneByUserAndUrl(user, shareUrl).orElseThrow();
        portfolio.updateSharing(sharing);
    }

    public boolean addBlock(String shareUrl, String blockId) {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        /* shareUrl??? ???????????? ?????????????????? ????????? ?????? ????????? exception */
        boolean isMine = portfolioRepository.checkIsMine(shareUrl, user);
        if (!isMine) throw new ForbiddenException();

        /* TODO: blockId??? ???????????? ????????? ????????? ?????? ????????? exception */
        /* ??????????????? ????????? ???????????? ????????? */




        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        /* ?????? ????????? block?????? throw exception */
        if (portfolio.blockExists(blockId)) {
            return false;
        }

        portfolio.addNewBlock(blockId);
        return true;
    }

    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl).orElseThrow();
        portfolio.removeBlock(blockId);
    }
}

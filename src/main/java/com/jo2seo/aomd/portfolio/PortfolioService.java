package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.portfolio.dto.GetAllResponse;
import com.jo2seo.aomd.security.SecurityUtil;
import com.jo2seo.aomd.user.User;
import com.jo2seo.aomd.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public String createNewPortfolio() {
        String userEmail = SecurityUtil.getCurrentEmail().orElseThrow();
        User user = userRepository.findByEmail(userEmail).orElseThrow();

        String title = "new Portfolio";

        Portfolio portfolio = new Portfolio(user, title);
        portfolioRepository.save(portfolio);
        return portfolio.getShareUrl();
    }

    public void updateTitle(String shareUrl, String title) {
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl).orElseThrow();
        portfolio.updateTitle(title);
    }

}

package com.jo2seo.aomd.portfolio;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @PostMapping("/portfolio")
    public Long createPortfolio() {
        Long portfolioId = portfolioService.createNewPortfolio();
        return portfolioId;
    }
}

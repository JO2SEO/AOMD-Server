package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.portfolio.dto.GetAllResponse;
import com.jo2seo.aomd.portfolio.dto.PatchPortfolioTitleRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/portfolio")
    public BaseResponse getPortfolioAll() {
        List<GetAllResponse> res = portfolioService.getAll();
        return new BaseResponse(res);
    }

    @PostMapping("/portfolio")
    public Long createPortfolio() {
        Long portfolioId = portfolioService.createNewPortfolio();
        return portfolioId;
    }

    @GetMapping("/portfolio/url/{id}")
    public String getPortfolioUrl(
            @PathVariable("id") Long id
    ) {
        return portfolioService.getShareUrl(id);
    }

    @PatchMapping("/portfolio/title/{id}")
    public void patchPortfolioTitle(
            @PathVariable("id") Long id,
            @Valid @RequestBody PatchPortfolioTitleRequest patchPortfolioTitleRequest
    ) {
        portfolioService.updateTitle(id, patchPortfolioTitleRequest.getTitle());
    }
}

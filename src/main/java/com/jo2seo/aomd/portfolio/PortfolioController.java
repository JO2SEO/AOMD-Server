package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.portfolio.dto.FindOneByShareUrlResponse;
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
    public BaseResponse<List<GetAllResponse>> getPortfolioAll() {
        List<GetAllResponse> res = portfolioService.getAll();
        return new BaseResponse(res);
    }

    @GetMapping("/portfolio/{id}")
    public BaseResponse<FindOneByShareUrlResponse> getPortfolioByUrl(
            @PathVariable("id") String shareUrl
    ) {
        return new BaseResponse(portfolioService.findOneByShareUrl(shareUrl));
    }

    @PostMapping("/portfolio")
    public BaseResponse<String> createPortfolio() {
        String shareUrl = portfolioService.createNewPortfolio();
        return new BaseResponse(shareUrl);
    }

    @PatchMapping("/portfolio/title/{id}")
    public void patchPortfolioTitle(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioTitleRequest patchPortfolioTitleRequest
    ) {
        portfolioService.updateTitle(shareUrl, patchPortfolioTitleRequest.getTitle());
    }
}

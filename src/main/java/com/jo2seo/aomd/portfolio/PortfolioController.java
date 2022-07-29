package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.portfolio.dto.*;
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

    @PatchMapping(value = "/portfolio/{id}/title")
    public void patchPortfolioTitle(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioTitleRequest patchPortfolioTitleRequest
    ) {
        portfolioService.updateTitle(shareUrl, patchPortfolioTitleRequest.getTitle());
    }

    @PatchMapping(value = "/portfolio/{id}/order")
    public void patchPortfolioOrder(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioOrderRequest patchPortfolioOrderRequest
    ) throws BaseException {
        portfolioService.updateOrder(shareUrl, patchPortfolioOrderRequest.getChainIdList());
    }

    @PatchMapping(value = "/portfolio/{id}/sharing")
    public void patchPortfolioSharing(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioSharedRequest patchPortfolioSharedRequest
    ) {
        portfolioService.updateShared(shareUrl, patchPortfolioSharedRequest.isShared());
    @PostMapping("/portfolio/{id}/block")
    public void postPortfolioChain(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PostPortfolioBlockRequest postPortfolioBlockRequest
    ) throws BaseException {
        portfolioService.addBlock(shareUrl, postPortfolioBlockRequest.getBlockId());
    }
}

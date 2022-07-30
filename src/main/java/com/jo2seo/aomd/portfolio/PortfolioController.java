package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.portfolio.dto.request.*;
import com.jo2seo.aomd.portfolio.dto.response.FindOneByShareUrlResponse;
import com.jo2seo.aomd.portfolio.dto.response.GetAllResponse;
import com.jo2seo.aomd.portfolio.dto.response.PatchPortfolioResponse;
import com.jo2seo.aomd.portfolio.dto.response.PostPortfolioResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PortfolioController {
    private final PortfolioService portfolioService;

    @GetMapping("/portfolio")
    public ResponseEntity<List<GetAllResponse>> getPortfolioAll() {
        List<GetAllResponse> res = portfolioService.getAll();
        return ResponseEntity.ok(res);
    }

    /*
    case 1) URL에 해당하는 포트폴리오가 자기 것
    그냥 정보 다 내려줌
    case 2) URL에 해당하는 포트폴리오가 남의 것
    case 2-1) sharing이 true면 정보 다 내려줌
    case 2-2) sharing이 false면 sharing를 false로 하고 아무것도 안 내려줌
    */
    @GetMapping("/portfolio/{id}")
    public ResponseEntity<FindOneByShareUrlResponse> getPortfolioByUrl(
            @PathVariable("id") String shareUrl
    ) {
        boolean isMine = portfolioService.checkMine(shareUrl);
        boolean isSharing = portfolioService.checkSharing(shareUrl);
        if (!isMine && !isSharing) {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new FindOneByShareUrlResponse(true, null, false, null, null, null));
        }
        return ResponseEntity.ok(portfolioService.findOneByShareUrl(shareUrl, isMine));
    }

    @PostMapping("/portfolio")
    public ResponseEntity<PostPortfolioResponse> postPortfolio() {
        Portfolio portfolio = portfolioService.createNewPortfolio();
        return ResponseEntity.ok(new PostPortfolioResponse(portfolio.getShareUrl(), portfolio.getTitle()));
    }

    @PatchMapping(value = "/portfolio/{id}")
    public ResponseEntity<PatchPortfolioResponse> patchPortfolio(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioRequest patchPortfolioRequest
    ) {
        if (patchPortfolioRequest.getTitle() != null) {
            portfolioService.updateTitle(shareUrl, patchPortfolioRequest.getTitle());
        }
        if (patchPortfolioRequest.getSharing() != null) {
            portfolioService.updateSharing(shareUrl, patchPortfolioRequest.getSharing());
        }
        if (patchPortfolioRequest.getOrder() != null) {
            portfolioService.updateOrder(shareUrl, patchPortfolioRequest.getOrder());
        }
        return ResponseEntity.ok(new PatchPortfolioResponse(portfolioService.findIdByShareUrl(shareUrl)));
    }


    @PostMapping("/portfolio/{id}/block")
    public void postPortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PostPortfolioBlockRequest postPortfolioBlockRequest
    ) throws BaseException {
        portfolioService.addBlock(shareUrl, postPortfolioBlockRequest.getBlockId());
    }

    @DeleteMapping("/portfolio/{id}/block")
    public void deletePortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody DeletePortfolioBlockRequest deletePortfolioBlockRequest
    ) throws BaseException {
        portfolioService.deleteBlock(shareUrl, deletePortfolioBlockRequest.getBlockId());
    }
}

package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponse;
import com.jo2seo.aomd.portfolio.dto.request.*;
import com.jo2seo.aomd.portfolio.dto.response.FindOneByShareUrlResponse;
import com.jo2seo.aomd.portfolio.dto.response.GetAllResponse;
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
        portfolioService.updateOrder(shareUrl, patchPortfolioOrderRequest.getBlockIdList());
    }

    @PatchMapping(value = "/portfolio/{id}/sharing")
    public void patchPortfolioSharing(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioSharingRequest patchPortfolioSharingRequest
    ) {
        portfolioService.updateSharing(shareUrl, patchPortfolioSharingRequest.isSharing());
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

package jo2seo.aomd.controller.portfolio;

import jo2seo.aomd.api.portfolio.PortfolioController;
import jo2seo.aomd.api.portfolio.dto.*;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.service.portfolio.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class PortfolioControllerImpl implements PortfolioController {
    
    private final PortfolioService portfolioService;

    @Override
    public ResponseEntity getPortfolioAll() {
        List<GetAllPortfolioTitle> res = portfolioService.getAll();
        return new ResponseEntity<>(res, OK);
    }

    /*
    case 1) URL에 해당하는 포트폴리오가 자기 것
    그냥 정보 다 내려줌
    case 2) URL에 해당하는 포트폴리오가 남의 것
    case 2-1) sharing이 true면 정보 다 내려줌
    case 2-2) sharing이 false면 sharing를 false로 하고 아무것도 안 내려줌
    */
    @Override
    public ResponseEntity getPortfolioByUrl(String shareUrl) {
        boolean isMine = portfolioService.checkMine(shareUrl);
        boolean isSharing = portfolioService.checkSharing(shareUrl);

        PortfolioUrlValidate<PortfolioService> portfolioServiceFilter = 
                (PortfolioService portfolioService1) 
                        -> portfolioService1.checkMine(shareUrl) && portfolioService1.checkSharing(shareUrl);

        if (!isMine && !isSharing) {
            return new ResponseEntity<>(FORBIDDEN);
        }
        else {
            return new ResponseEntity<>(portfolioService.findOneByShareUrl(shareUrl, isMine), OK);
        }
    }

    @Override
    public ResponseEntity createPortfolio() {
        Portfolio portfolio = portfolioService.createNewPortfolio();
        return new ResponseEntity(new PostPortfolioResponse(portfolio.getShareUrl(), portfolio.getTitle()), OK);
    }

    @Override
    public ResponseEntity updatePortfolio(String shareUrl, PatchPortfolioRequest patchPortfolioRequest) {
        if (patchPortfolioRequest.getTitle() != null) {
            portfolioService.updateTitle(shareUrl, patchPortfolioRequest.getTitle());
        }
        if (patchPortfolioRequest.getSharing() != null) {
            portfolioService.updateSharing(shareUrl, patchPortfolioRequest.getSharing());
        }
        if (patchPortfolioRequest.getOrder() != null) {
            portfolioService.updateOrder(shareUrl, patchPortfolioRequest.getOrder());
        }
        return new ResponseEntity(new PatchPortfolioResponse(portfolioService.findIdByShareUrl(shareUrl)), OK);
    }


    @Override
    public ResponseEntity createPortfolioBlock(String shareUrl, PostPortfolioBlockRequest postPortfolioBlockRequest) {
        boolean added = portfolioService.addBlock(shareUrl, postPortfolioBlockRequest.getBlockId());
        if (added) {
            return new ResponseEntity(CREATED);
        }
        else {
            return new ResponseEntity(NO_CONTENT);
        }
    }

    @Override
    public ResponseEntity deletePortfolioBlock(
            String shareUrl, 
            DeletePortfolioBlockRequest deletePortfolioBlockRequest) {
        boolean isMine = portfolioService.checkMine(shareUrl);
        if (!isMine) {
            return new ResponseEntity(FORBIDDEN);
        }
        portfolioService.deleteBlock(shareUrl, deletePortfolioBlockRequest.getBlockId());
        return new ResponseEntity(NO_CONTENT);
    }
}

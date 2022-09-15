package jo2seo.aomd.controller.portfolio;

import jo2seo.aomd.api.portfolio.PortfolioController;
import jo2seo.aomd.api.portfolio.dto.*;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.security.SecurityUtil;
import jo2seo.aomd.service.portfolio.PortfolioCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioControllerImpl implements PortfolioController {
    
    private final PortfolioCRUDService portfolioService;


    @Override
    public ResponseEntity getPortfolioAll() {
        List<GetAllPortfolioTitle> allPortfolio = portfolioService.findAll();
        return new ResponseEntity(allPortfolio, OK);
    }

    @Override
    public ResponseEntity sharedPortfolioOpen(String shareUrl) {
        Portfolio portfolio = portfolioService.checkSharingAndGet(shareUrl);
        return new ResponseEntity(portfolio, OK);
    }

    @Override
    public ResponseEntity openMyPortFolio(Long portfolioId) {
        String userEmail = SecurityUtil.getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioService.checkMineAndGet(userEmail, portfolioId);
        return new ResponseEntity(portfolio, OK);
    }

    @Override
    public ResponseEntity createPortfolio(
            CreatePortfolioRequest createPortfolioRequest) {
        Portfolio portfolio = portfolioService.createNewPortfolio(createPortfolioRequest);
        return new ResponseEntity(portfolio, CREATED);
    }

    @Override
    public ResponseEntity createPortfolioBlock(
            String shareUrl, 
            CreatePortfolioBlockRequest createPortfolioBlockRequest) {
        Portfolio portfolio =
                portfolioService.addBlock(shareUrl, createPortfolioBlockRequest.getBlockId());
        return new ResponseEntity(portfolio, CREATED);
    }

    @Override
    public ResponseEntity updatePortfolio(
            String shareUrl, 
            UpdatePortfolioRequest updatePortfolioRequest) {
        Portfolio portfolio = 
                portfolioService.update(shareUrl, updatePortfolioRequest);
        return new ResponseEntity(portfolio, CREATED);
    }
    
    @Override
    public ResponseEntity deletePortfolioBlock(
            String shareUrl,
            DeletePortfolioBlockRequest deletePortfolioBlockRequest) {
        portfolioService.deleteBlock(shareUrl, deletePortfolioBlockRequest.getBlockId());
        return new ResponseEntity(NO_CONTENT);
    }
}

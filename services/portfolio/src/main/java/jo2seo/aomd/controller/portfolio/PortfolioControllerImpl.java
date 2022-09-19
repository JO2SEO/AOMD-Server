package jo2seo.aomd.controller.portfolio;

import jo2seo.aomd.api.portfolio.PortfolioController;
import jo2seo.aomd.api.portfolio.dto.CreatePortfolioBlockRequest;
import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.api.portfolio.dto.DeletePortfolioBlockRequest;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.domain.Block.dto.AwardDto;
import jo2seo.aomd.domain.Block.dto.BlockCompositeDto;
import jo2seo.aomd.domain.Block.dto.EducationDto;
import jo2seo.aomd.domain.Block.dto.LicenseDto;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.service.portfolio.PortfolioBlockchainService;
import jo2seo.aomd.service.portfolio.PortfolioCRUDService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static jo2seo.aomd.security.SecurityUtil.getCurrentEmail;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioControllerImpl implements PortfolioController {
    
    private final PortfolioCRUDService portfolioService;

    private final PortfolioBlockchainService portfolioBlockchainService;

    public ResponseEntity getPortfolioBlockByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        List<AwardDto> awardList = 
                portfolioBlockchainService.findAwardListByMemberId(memberEmail);
        List<EducationDto> educationList = 
                portfolioBlockchainService.findEducationListByMemberId(memberEmail);
        List<LicenseDto> licenseList = 
                portfolioBlockchainService.findLicenseListByMemberId(memberEmail);

        BlockCompositeDto blockCompositeDto = new BlockCompositeDto(awardList, educationList, licenseList);

        return new ResponseEntity(blockCompositeDto, OK);
    }
    
    @Override
    public ResponseEntity findAllMyPortfolioByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        return new ResponseEntity(portfolioService.findAllPortfolioByMember(memberEmail), OK);
    }

    @Override
    public ResponseEntity getSimplePortfolioAllByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        return new ResponseEntity(portfolioService.findSimplePortfolioAllByMember(memberEmail), OK);
    }

    @Override
    public ResponseEntity sharedPortfolioOpen(String shareUrl) {
        Portfolio portfolio = portfolioService.checkSharingAndGet(shareUrl);
        return new ResponseEntity(portfolio, OK);
    }

    @Override
    public ResponseEntity openMyPortFolio(Long portfolioId) {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioService.checkMineAndGet(memberEmail, portfolioId);
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

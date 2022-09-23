package jo2seo.aomd.controller.portfolio;

import jo2seo.aomd.api.portfolio.PortfolioController;
import jo2seo.aomd.api.portfolio.block.dto.BlockCompositeDto;
import jo2seo.aomd.api.portfolio.dto.*;
import jo2seo.aomd.api.resume.dto.ResumeDto;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.service.portfolio.PortfolioBlockchainService;
import jo2seo.aomd.service.portfolio.PortfolioCRUDService;
import jo2seo.aomd.service.portfolio.mapper.PortfolioMapper;
import jo2seo.aomd.service.resume.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static jo2seo.aomd.security.SecurityUtil.getCurrentEmail;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PortfolioControllerImpl implements PortfolioController {
    
    private final PortfolioCRUDService portfolioService;

    private final PortfolioBlockchainService portfolioBlockchainService;

    private final PortfolioMapper portfolioMapper;
    
    private final ResumeMapper resumeMapper;

    
    public ResponseEntity getBlockCompositeDtoByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);

        BlockCompositeDto blockCompositeDto = 
                portfolioBlockchainService.findBlockCompositeDto(memberEmail);

        return new ResponseEntity(blockCompositeDto, OK);
    }

    /**
     * 현재 유저의 모든 포트폴리오 정보를 넘겨준다.
     * 제목, 자소서, 포폴이 사용하는 블록들의 정보
     * @return
     */
    @Override
    public ResponseEntity findAllMyPortfolioByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);

        BlockCompositeDto blockCompositeDto = 
                portfolioBlockchainService.findBlockCompositeDto(memberEmail);

        List<Portfolio> portfolioList = 
                portfolioService.findAllPortfolioByMember(memberEmail);

        List<PortfolioCompositeDto> portfolioCompositeDtoList = portfolioList.stream()
                .map(p -> {
                    PortfolioDto portfolioDto = portfolioMapper.entityToDTO(p);
                    BlockCompositeDto usingBlockListDto = p.getUsingBlockList(blockCompositeDto);
                    List<ResumeDto> resumeList =
                            p.getResumeList().stream()
                                    .map(r -> {
                                        ResumeDto resumeDto = resumeMapper.entityToDTO(r);
                                        resumeDto.setPortfolioId(portfolioDto.getPortfolioId());
                                        return resumeDto;
                                    }).collect(Collectors.toList());
                    return new PortfolioCompositeDto(portfolioDto, usingBlockListDto, resumeList);
                }).collect(Collectors.toList());


        return new ResponseEntity(portfolioCompositeDtoList, OK);
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
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioService.createNewPortfolio(memberEmail, createPortfolioRequest);
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
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = 
                portfolioService.update(memberEmail, shareUrl, updatePortfolioRequest);
        return new ResponseEntity(portfolio, CREATED);
    }
    
    @Override
    public ResponseEntity deletePortfolioBlock(
            String shareUrl,
            DeletePortfolioBlockRequest deletePortfolioBlockRequest) {
        portfolioService.deleteBlock(shareUrl, deletePortfolioBlockRequest.getBlockId());
        return new ResponseEntity(NO_CONTENT);
    }

//    private BlockCompositeDto createBlockComposite(String memberEmail) {
//        
//    }
}

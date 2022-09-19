package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.dto.*;
import jo2seo.aomd.api.resume.dto.ResumeDto;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.exception.Member.NoAuthenticationException;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import jo2seo.aomd.service.portfolio.mapper.PortfolioBlockMapper;
import jo2seo.aomd.service.portfolio.mapper.PortfolioMapper;
import jo2seo.aomd.service.portfolio.validation.PortfolioValidator;
import jo2seo.aomd.service.resume.mapper.ResumeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static jo2seo.aomd.security.SecurityUtil.getCurrentEmail;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioCRUDServiceImpl implements PortfolioCRUDService {

    private final PortfolioRepository portfolioRepository;
    private final MemberRepository memberRepository;
    private final PortfolioValidator portfolioValidator;
    private final PortfolioMapper portfolioMapper;
    private final PortfolioBlockMapper portfolioBlockMapper;
    private final ResumeMapper resumeMapper;
    
    
    @Override
    public List<PortfolioCompositeDto> findAllPortfolioByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(NoMatchingPortfolioException::new);

        return portfolioRepository.findAllByMember(member).stream()
                .map(p -> {
                    PortfolioDto portfolioDto = portfolioMapper.entityToDTO(p);
                    List<PortfolioBlockDto> portfolioBlockList = 
                            p.getPortfolioBlockList().stream()
                                    .map(portfolioBlockMapper::entityToDTO)
                                    .collect(Collectors.toList());
                    List<ResumeDto> resumeList = 
                            p.getResumeList().stream()
                                    .map(r -> {
                                        ResumeDto resumeDto = resumeMapper.entityToDTO(r);
                                        resumeDto.setPortfolioId(portfolioDto.getPortfolioId());
                                        return resumeDto;
                                    }).collect(Collectors.toList());
                    return new PortfolioCompositeDto(portfolioDto, portfolioBlockList, resumeList);
                })
                .collect(Collectors.toList());
    }
    @Override
    public List<PortfolioTitleDto> findSimplePortfolioAllByMember() {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(NoMatchingPortfolioException::new);
        
        return portfolioRepository.findAllByMember(member).stream()
                .map(Portfolio::createOnlyTitleDto)
                .collect(Collectors.toList());
    }

    @Override
    public Portfolio checkMineAndGet(String memberEmail, Long portfolioId) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkMine(memberEmail, portfolio);
        
        return portfolio;
    }

    @Override
    public Portfolio checkSharingAndGet(String shareUrl) {
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
        portfolioValidator.checkSharing(portfolio);
        
        return portfolio;
    }

    @Override
    public Portfolio findByUrl(String shareUrl) {
        return portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
    }

    @Override
    public Portfolio findById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId)
                .orElseThrow(NoMatchingPortfolioException::new);
    }
    
    @Override
    @Transactional
    public Portfolio createNewPortfolio(CreatePortfolioRequest createPortfolioRequest) {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        String title = createPortfolioRequest.getTitle();
        Portfolio portfolio = new Portfolio(member, title);

        portfolioRepository.save(portfolio);
        return portfolio;
    }

    @Override
    @Transactional
    public Portfolio addBlock(String shareUrl, String blockId) {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolioValidator.checkMine(memberEmail, portfolio);

        /* TODO: blockId에 해당하는 블록이 자신의 것이 아니면 exception */
        /* 블록체인과 연결이 구현되면 가능함 */


        portfolio.addNewBlock(blockId);
        return portfolio;
    }


    @Override
    @Transactional
    public Portfolio update(String shareUrl, UpdatePortfolioRequest updatePortfolioRequest) {
        String memberEmail = getCurrentEmail()
                .orElseThrow(NoAuthenticationException::new);

        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        Portfolio portfolio = portfolioRepository.findByMemberAndShareUrl(member, shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.updateAll(updatePortfolioRequest);

        return portfolio;
    }


    @Override
    @Transactional
    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findByShareUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);

        portfolio.removeBlock(blockId);
    }
}

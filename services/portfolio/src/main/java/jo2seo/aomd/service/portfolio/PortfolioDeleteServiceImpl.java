package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.exception.portfolio.NoMatchingPortfolioException;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioDeleteServiceImpl implements PortfolioDeleteService {

    private final PortfolioRepository portfolioRepository;
    
    @Override
    public void deleteBlock(String shareUrl, String blockId) {
        Portfolio portfolio = portfolioRepository.findOneByUrl(shareUrl)
                .orElseThrow(NoMatchingPortfolioException::new);
        
        portfolio.removeBlock(blockId);
    }
}

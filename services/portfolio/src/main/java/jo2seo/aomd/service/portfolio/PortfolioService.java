package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.api.portfolio.dto.FindOneByShareUrlResponse;
import jo2seo.aomd.api.portfolio.dto.GetAllPortfolioTitle;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface PortfolioService {
    @Transactional(readOnly = true)
    List<GetAllPortfolioTitle> getAll();
    
    @Transactional(readOnly = true)
    boolean checkMine(String shareUrl);

    @Transactional(readOnly = true)
    boolean checkSharing(String shareUrl);

    @Transactional(readOnly = true)
    String findIdByShareUrl(String shareUrl);

    @Transactional(readOnly = true)
    FindOneByShareUrlResponse findOneByShareUrl(String shareUrl, boolean isMine);

    Portfolio createNewPortfolio();

    void updateTitle(String shareUrl, String title);

    void updateOrder(String shareUrl, List<String> blockIdList);
    
    void updateSharing(String shareUrl, boolean sharing);

    boolean addBlock(String shareUrl, String blockId);

    void deleteBlock(String shareUrl, String blockId);
}

package jo2seo.aomd.api.portfolio;

import jo2seo.aomd.api.portfolio.dto.CreatePortfolioBlockRequest;
import jo2seo.aomd.api.portfolio.dto.CreatePortfolioRequest;
import jo2seo.aomd.api.portfolio.dto.DeletePortfolioBlockRequest;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
public interface PortfolioController {

    
    @GetMapping("/portfolio")
    ResponseEntity findAllMyPortfolioByMember();
    
    @GetMapping("/simple-portfolio")
    ResponseEntity getSimplePortfolioAllByMember();

    @GetMapping("/portfolio/{shareUrl}")
    ResponseEntity sharedPortfolioOpen(@PathVariable("shareUrl") String shareUrl);

    @PostMapping("/portfolio")
    ResponseEntity createPortfolio(@RequestBody CreatePortfolioRequest createPortfolioRequest);

    @GetMapping("/portfolio/me/{id}")
    ResponseEntity openMyPortFolio(@PathVariable("id") Long portfolioId);

    @PatchMapping(value = "/portfolio/{id}")
    ResponseEntity updatePortfolio(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody UpdatePortfolioRequest updatePortfolioRequest
    );

    @PostMapping("/portfolio/{id}/block")
    ResponseEntity createPortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody CreatePortfolioBlockRequest createPortfolioBlockRequest
    );

    @DeleteMapping("/portfolio/{id}/block")
    ResponseEntity deletePortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody DeletePortfolioBlockRequest deletePortfolioBlockRequest
    );
}

package jo2seo.aomd.api.portfolio;

import jo2seo.aomd.api.portfolio.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
public interface PortfolioController {

    @GetMapping("/portfolio")
    ResponseEntity getPortfolioAll();

    @GetMapping("/portfolio/{id}")
    ResponseEntity getPortfolioByUrl(
            @PathVariable("id") String shareUrl
    );

    @PostMapping("/portfolio")
    ResponseEntity createPortfolio();

    @PatchMapping(value = "/portfolio/{id}")
    ResponseEntity updatePortfolio(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PatchPortfolioRequest patchPortfolioRequest
    );

    @PostMapping("/portfolio/{id}/block")
    ResponseEntity createPortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody PostPortfolioBlockRequest postPortfolioBlockRequest
    );

    @DeleteMapping("/portfolio/{id}/block")
    ResponseEntity deletePortfolioBlock(
            @PathVariable("id") String shareUrl,
            @Valid @RequestBody DeletePortfolioBlockRequest deletePortfolioBlockRequest
    );
}

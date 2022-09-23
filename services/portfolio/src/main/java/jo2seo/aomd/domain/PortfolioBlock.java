package jo2seo.aomd.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioBlockRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PortfolioBlock {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_block_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String blockId;

    public PortfolioBlock(Portfolio portfolio, String blockId) {
        this.portfolio = portfolio;
        this.blockId = blockId;
    }

    public PortfolioBlock(Portfolio portfolio, UpdatePortfolioBlockRequest newBlocks) {
        this.portfolio = portfolio;
        this.blockId = newBlocks.getBlockId();
    }
}

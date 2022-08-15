package com.jo2seo.aomd.domain;

import com.jo2seo.aomd.domain.Portfolio;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class PortfolioBlockOrder {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_block_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String blockId;

    public PortfolioBlockOrder(Portfolio portfolio, String blockId) {
        this.portfolio = portfolio;
        this.blockId = blockId;
    }
}

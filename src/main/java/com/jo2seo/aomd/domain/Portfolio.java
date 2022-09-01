package com.jo2seo.aomd.domain;

import com.jo2seo.aomd.exception.ForbiddenException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Portfolio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    private String title;

    @NotNull
    private Boolean sharing = false;

    @NotNull
    private String shareUrl = String.valueOf(UUID.randomUUID());

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "orderIndex")
    private List<PortfolioBlockOrder> portfolioBlockOrderList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Portfolio(User user, String title) {
        this.user = user;
        this.title = title;
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateOrder(List<String> blockList) throws ForbiddenException {
        if (blockList.size() != portfolioBlockOrderList.size()) {
            throw new ForbiddenException();
        }

        for (String blockId : blockList) {
            boolean anyMatch = portfolioBlockOrderList.stream().anyMatch(portfolioBlockOrder -> portfolioBlockOrder.getBlockId().equals(blockId));
            if (!anyMatch) {
                throw new ForbiddenException();
            }
        }
        portfolioBlockOrderList.sort(Comparator.comparingInt(o -> blockList.indexOf(o.getBlockId())));
    }

    public void updateSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public boolean blockExists(String blockId) {
        return portfolioBlockOrderList.stream()
                .anyMatch(p -> p.getBlockId().equals(blockId));
    }

    public void addNewBlock(String blockId) {
        portfolioBlockOrderList.add(new PortfolioBlockOrder(this, blockId));
    }

    public void removeBlock(String blockId) {
        portfolioBlockOrderList.removeIf(p -> p.getBlockId().equals(blockId));
    }
}

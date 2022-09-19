package jo2seo.aomd.domain;

import jo2seo.aomd.api.portfolio.dto.PortfolioCompositeDto;
import jo2seo.aomd.api.portfolio.dto.PortfolioTitleDto;
import jo2seo.aomd.api.portfolio.dto.UpdatePortfolioRequest;
import jo2seo.aomd.exception.portfolio.BlockExistException;
import jo2seo.aomd.exception.portfolio.BlockListNotMatchingException;
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
import java.util.stream.Collectors;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Portfolio {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "portfolio_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String title;

    @NotNull
    private Boolean sharing = false;

    @NotNull
    private String shareUrl = String.valueOf(UUID.randomUUID());

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderColumn(name = "orderIndex")
    private final List<PortfolioBlock> portfolioBlockList = new ArrayList<>();

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Resume> resumeList = new ArrayList<>();

    @CreatedDate
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    public Portfolio(Member member, String title) {
        this.member = member;
        this.title = title;
    }

    public PortfolioTitleDto createOnlyTitleDto() {
        return new PortfolioTitleDto(shareUrl, title);
    }
    
    public void updateAll(UpdatePortfolioRequest request) {
        updateTitle(request.getTitle());
        updateOrder(request.getOrder());
        updateSharing(request.getSharing());
    }
    
    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateOrder(List<String> blockList) {
        if (blockList.size() != portfolioBlockList.size()) {
            throw new BlockListNotMatchingException();
        }
        
        blockList.forEach(
            (blockId) -> {
                boolean anyMatch = portfolioBlockList.stream()
                        .anyMatch(portfolioBlock -> portfolioBlock.getBlockId().equals(blockId));
                if (!anyMatch) throw new BlockListNotMatchingException();
            }
        );
        
        portfolioBlockList.sort(Comparator.comparingInt(o -> blockList.indexOf(o.getBlockId())));
    }

    public void updateSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public boolean blockExists(String blockId) {
        return portfolioBlockList.stream()
                .anyMatch(p -> p.getBlockId().equals(blockId));
    }

    public void addNewBlock(String blockId) {
        List<PortfolioBlock> exists = portfolioBlockList.stream()
                .filter(pbo -> pbo.getBlockId().equals(blockId))
                .collect(Collectors.toList());
        if(!exists.isEmpty()) portfolioBlockList.add(new PortfolioBlock(this, blockId));
        else throw new BlockExistException();
    }

    public void removeBlock(String blockId) {
        portfolioBlockList.removeIf(p -> p.getBlockId().equals(blockId));
    }
}

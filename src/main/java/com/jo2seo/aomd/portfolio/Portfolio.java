package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.BaseException;
import com.jo2seo.aomd.BaseResponseStatus;
import com.jo2seo.aomd.user.User;
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

    @OneToMany(mappedBy = "portfolio")
    @OrderColumn(name = "orderIndex")
    private List<PortfolioChainOrder> portfolioChainOrderList = new ArrayList<>();

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

    public void updateOrder(List<String> chainIdList) throws BaseException {
        if (chainIdList.size() != portfolioChainOrderList.size()) throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
        for (String chainId : chainIdList) {
            boolean anyMatch = portfolioChainOrderList.stream().anyMatch(portfolioChainOrder -> portfolioChainOrder.getChainId().equals(chainId));
            if (!anyMatch) {
                throw new BaseException(BaseResponseStatus.REQUEST_ERROR);
            }
        }
        portfolioChainOrderList.sort(Comparator.comparingInt(o -> chainIdList.indexOf(o.getChainId())));
    }

    public void updateSharing(boolean sharing) {
        this.sharing = sharing;
    }

    public void addNewBlock(PortfolioChainOrder portfolioChainOrder) {
        portfolioChainOrderList.add(portfolioChainOrder);
    }
}

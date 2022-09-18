package jo2seo.aomd.domain.Block.dto;

import jo2seo.aomd.domain.Block.Award;
import jo2seo.aomd.domain.Block.BlockType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jo2seo.aomd.domain.Block.BlockType.AWARD;
import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
@NoArgsConstructor
public class AwardDto implements BlockDto {

    private final static BlockType type = AWARD;
    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String rank;

    public AwardDto(Award award) {
        this.id = award.getId();
        this.title = award.getTitle();
        this.ownerId = award.getOwnerId();
        this.publisher = award.getPublisher();
        this.publishedAt = milliToDateTime(award.getPublishedAt());
        this.createdAt = milliToDateTime(award.getCreatedAt());
        this.rank = award.getRank();
    }

    @Override
    public String toString() {
        return "AwardDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", rank='" + rank + '\'' +
                '}';
    }
}

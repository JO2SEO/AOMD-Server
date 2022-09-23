package jo2seo.aomd.domain.Block;

import jo2seo.aomd.api.portfolio.block.dto.AwardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Award implements Block {

    private String id;
    private String title;
    private String ownerId;
    private String publisher;
    private Long publishedAt;
    private Long createdAt;
    private String rank;

    @Override
    public AwardDto toDto() {
        return new AwardDto(
                this.id,
                this.title,
                this.ownerId,
                this.publisher,
                milliToDateTime(this.publishedAt),
                milliToDateTime(this.createdAt),
                this.rank
        );
    }

    @Override
    public String toString() {
        return "Award{" +
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

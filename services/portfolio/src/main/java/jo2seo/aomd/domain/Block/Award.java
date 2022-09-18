package jo2seo.aomd.domain.Block;

import jo2seo.aomd.domain.Block.dto.AwardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Award implements Block {

    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private Long publishedAt;
    private Long createdAt;
    private String rank;

    @Override
    public AwardDto toDto() {
        return new AwardDto(this);
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

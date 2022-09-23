package jo2seo.aomd.api.portfolio.block.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jo2seo.aomd.api.portfolio.block.dto.BlockType.AWARD;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AwardDto implements BlockDto {

    private final static BlockType type = AWARD;
    private String id;
    private String title;
    private String ownerEmail;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String rank;


    @Override
    public String toString() {
        return "AwardDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerEmail +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", rank='" + rank + '\'' +
                '}';
    }
}

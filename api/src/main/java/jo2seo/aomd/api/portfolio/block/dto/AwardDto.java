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

    private final BlockType type = AWARD;
    private int oNumber;
    private String id;
    private String title;
    private String ownerEmail;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String rank;

    public AwardDto(String id, String title, String ownerEmail, String publisher, LocalDateTime publishedAt, LocalDateTime createdAt, String rank) {
        this.id = id;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.publisher = publisher;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.rank = rank;
    }

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

    @Override
    public void setONumber(int i) {
        this.oNumber = i;
    }
}

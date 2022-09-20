package jo2seo.aomd.api.portfolio.block.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jo2seo.aomd.api.portfolio.block.dto.BlockType.LICENSE;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseDto implements BlockDto {

    private final static BlockType type = LICENSE;
    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String description;
    private LocalDateTime expireDate;
    private String qualificationNumber;
    

    @Override
    public String toString() {
        return "LicenseDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", expireDate=" + expireDate +
                ", qualificationNumber='" + qualificationNumber + '\'' +
                '}';
    }
}

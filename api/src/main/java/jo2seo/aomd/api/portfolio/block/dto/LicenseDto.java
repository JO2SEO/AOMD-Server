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

    private final BlockType type = LICENSE;
    private int oNumber;
    private String id;
    private String title;
    private String ownerEmail;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String description;
    private LocalDateTime expireDate;
    private String qualificationNumber;

    public LicenseDto(String id, String title, String ownerEmail, String publisher, LocalDateTime publishedAt, LocalDateTime createdAt, String description, LocalDateTime expireDate, String qualificationNumber) {
        this.id = id;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.publisher = publisher;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.description = description;
        this.expireDate = expireDate;
        this.qualificationNumber = qualificationNumber;
    }

    @Override
    public String toString() {
        return "LicenseDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerEmail +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", description='" + description + '\'' +
                ", expireDate=" + expireDate +
                ", qualificationNumber='" + qualificationNumber + '\'' +
                '}';
    }

    @Override
    public void setONumber(int i) {
        this.oNumber = i;
    }
}

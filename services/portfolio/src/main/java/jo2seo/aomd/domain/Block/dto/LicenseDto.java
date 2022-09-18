package jo2seo.aomd.domain.Block.dto;

import jo2seo.aomd.domain.Block.BlockType;
import jo2seo.aomd.domain.Block.License;
import lombok.Getter;

import java.time.LocalDateTime;

import static jo2seo.aomd.domain.Block.BlockType.LICENSE;
import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
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
    
    public LicenseDto(License license) {
        this.id = license.getId();
        this.title = license.getTitle();
        this.ownerId = license.getOwnerId();
        this.publisher = license.getPublisher();
        this.publishedAt = milliToDateTime(license.getPublishedAt());
        this.createdAt = milliToDateTime(license.getCreatedAt());
        this.description = license.getDescription();
        this.expireDate = milliToDateTime(license.getExpireDate());
        this.qualificationNumber = license.getQualificationNumber();
    }

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

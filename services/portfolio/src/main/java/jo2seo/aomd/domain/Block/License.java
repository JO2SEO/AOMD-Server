package jo2seo.aomd.domain.Block;

import jo2seo.aomd.api.portfolio.block.dto.LicenseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class License implements Block {

    private String id;
    private String title;
    private String ownerId;
    private String publisher;
    private Long publishedAt;
    private Long createdAt;
    private String description;
    private Long expireDate;
    private String qualificationNumber;

    @Override
    public LicenseDto toDto() {
        return new LicenseDto(
                this.id,
                this.title,
                this.ownerId,
                this.publisher,
                milliToDateTime(this.publishedAt),
                milliToDateTime(this.createdAt),
                this.description,
                milliToDateTime(this.expireDate),
                this.qualificationNumber
        );
    }
    
    @Override
    public String toString() {
        return "License{" +
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

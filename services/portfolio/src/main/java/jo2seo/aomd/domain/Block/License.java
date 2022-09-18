package jo2seo.aomd.domain.Block;

import jo2seo.aomd.domain.Block.dto.LicenseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class License implements Block {

    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private Long publishedAt;
    private Long createdAt;
    private String description;
    private Long expireDate;
    private String qualificationNumber;

    @Override
    public LicenseDto toDto() {
        return new LicenseDto(this);
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

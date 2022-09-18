package jo2seo.aomd.domain.Block.dto;

import jo2seo.aomd.domain.Block.BlockType;
import jo2seo.aomd.domain.Block.Education;
import lombok.Getter;

import java.time.LocalDateTime;

import static jo2seo.aomd.domain.Block.BlockType.EDUCATION;
import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
public class EducationDto implements BlockDto {
    
    private final static BlockType type = EDUCATION;
    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String state;
    private Long departmentInfo;
    
    public EducationDto(Education education) {
        this.id = education.getId();
        this.title = education.getTitle();
        this.ownerId = education.getOwnerId();
        this.publisher = education.getPublisher();
        this.publishedAt = milliToDateTime(education.getPublishedAt());
        this.createdAt = milliToDateTime(education.getCreatedAt());
        this.state = education.getState();
        this.departmentInfo = education.getDepartmentInfo();
    }

    @Override
    public String toString() {
        return "EducationDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerId +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", state='" + state + '\'' +
                ", departmentInfo=" + departmentInfo +
                '}';
    }
}

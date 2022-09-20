package jo2seo.aomd.domain.Block;

import jo2seo.aomd.api.portfolio.block.dto.EducationDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jo2seo.aomd.utils.TimeUtils.milliToDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Education implements Block {

    private String id;
    private String title;
    private Long ownerId;
    private String publisher;
    private Long publishedAt;
    private Long createdAt;
    private String state;
    private Long departmentInfo;

    @Override
    public EducationDto toDto() {
        return new EducationDto(
                this.id,
                this.title,
                this.ownerId,
                this.publisher,
                milliToDateTime(this.publishedAt),
                milliToDateTime(this.createdAt),
                this.state,
                this.departmentInfo
        );
    }

    @Override
    public String toString() {
        return "Education{" +
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

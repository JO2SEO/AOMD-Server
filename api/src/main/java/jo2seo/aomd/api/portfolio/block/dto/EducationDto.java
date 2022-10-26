package jo2seo.aomd.api.portfolio.block.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jo2seo.aomd.api.portfolio.block.dto.BlockType.EDUCATION;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EducationDto implements BlockDto {
    
    private final BlockType type = EDUCATION;
    private int oNumber;
    private String id;
    private String title;
    private String ownerEmail;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String state;
    private String departmentInfo;

    public EducationDto(String id, String title, String ownerEmail, String publisher, LocalDateTime publishedAt, LocalDateTime createdAt, String state, String departmentInfo) {
        this.id = id;
        this.title = title;
        this.ownerEmail = ownerEmail;
        this.publisher = publisher;
        this.publishedAt = publishedAt;
        this.createdAt = createdAt;
        this.state = state;
        this.departmentInfo = departmentInfo;
    }

    @Override
    public String toString() {
        return "EducationDto{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", ownerId=" + ownerEmail +
                ", publisher='" + publisher + '\'' +
                ", publishedAt=" + publishedAt +
                ", createdAt=" + createdAt +
                ", state='" + state + '\'' +
                ", departmentInfo=" + departmentInfo +
                '}';
    }

    @Override
    public void setONumber(int i) {
        this.oNumber = i;
    }
}

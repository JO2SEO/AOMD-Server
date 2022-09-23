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
    
    private final static BlockType type = EDUCATION;
    private String id;
    private String title;
    private String ownerEmail;
    private String publisher;
    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private String state;
    private String departmentInfo;

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
}
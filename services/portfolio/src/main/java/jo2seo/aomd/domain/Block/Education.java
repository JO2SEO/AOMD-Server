package jo2seo.aomd.domain.Block;

import lombok.Getter;

@Getter
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

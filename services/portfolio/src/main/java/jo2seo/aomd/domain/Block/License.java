package jo2seo.aomd.domain.Block;

import lombok.Getter;

@Getter
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

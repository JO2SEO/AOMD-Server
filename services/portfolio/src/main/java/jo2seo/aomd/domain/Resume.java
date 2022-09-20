package jo2seo.aomd.domain;

import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class Resume {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resume_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    private String question = "";

    @Lob
    private String content = "";

    public Resume(Portfolio portfolio, String question, String content) {
        this.portfolio = portfolio;
        this.question = question;
        this.content = content;
    }

    public Resume(Portfolio portfolio, UpdateResumeRequest newResume) {
        this.portfolio = portfolio;
        this.question = newResume.getQuestion();
        this.content = newResume.getContent();
    }
}

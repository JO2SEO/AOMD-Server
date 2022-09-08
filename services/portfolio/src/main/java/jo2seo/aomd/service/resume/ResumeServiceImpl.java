package jo2seo.aomd.service.resume;

import jo2seo.aomd.api.resume.dto.PostResumeRequest;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.Resume;
import jo2seo.aomd.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService{
    private final ResumeRepository resumeRepository;

    @Override
    public Long createResume(PostResumeRequest postResumeRequest) {
        Resume resume = new Resume(new Portfolio(), postResumeRequest.getQuestion(), postResumeRequest.getContent());
        resumeRepository.save(resume);
        return resume.getId();
    }
}

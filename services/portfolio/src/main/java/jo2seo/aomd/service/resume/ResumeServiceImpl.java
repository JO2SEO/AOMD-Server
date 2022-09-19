package jo2seo.aomd.service.resume;

import jo2seo.aomd.api.resume.dto.CreateResumeRequest;
import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.Resume;
import jo2seo.aomd.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {
    
    private final ResumeRepository resumeRepository;

    @Override
    public Long createResume(CreateResumeRequest createResumeRequest) {
        Resume resume = new Resume(new Portfolio(), createResumeRequest.getQuestion(), createResumeRequest.getContent());
        resumeRepository.save(resume);
        return resume.getId();
    }

    @Override
    public Long updateResume(Long resumeId, UpdateResumeRequest updateResumeRequest) {
        Optional<Resume> resume = resumeRepository.findById(resumeId);
        if (resume.isPresent()) {
            
        }
        return null;
    }
}

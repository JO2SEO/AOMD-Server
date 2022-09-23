package jo2seo.aomd.controller.resume;

import jo2seo.aomd.api.resume.ResumeController;
import jo2seo.aomd.api.resume.dto.CreateResumeRequest;
import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;
import jo2seo.aomd.service.resume.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResumeControllerImpl implements ResumeController {
    private final ResumeService resumeService;

    @Override
    public Long createResume(CreateResumeRequest createResumeRequest) {
        return resumeService.createResume(createResumeRequest);
    }

    @Override
    public Long updateResume(Long resumeId, UpdateResumeRequest updateResumeRequest) {
        return resumeService.updateResume(resumeId, updateResumeRequest);
    }
}

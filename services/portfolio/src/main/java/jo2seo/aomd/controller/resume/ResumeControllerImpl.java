package jo2seo.aomd.controller.resume;

import jo2seo.aomd.api.resume.ResumeController;
import jo2seo.aomd.api.resume.dto.PostResumeRequest;
import jo2seo.aomd.service.resume.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ResumeControllerImpl implements ResumeController {
    private final ResumeService resumeService;

    @Override
    public Long createResume(PostResumeRequest postResumeRequest) {
        return resumeService.createResume(postResumeRequest);
    }
}

package jo2seo.aomd.api.resume;

import jo2seo.aomd.api.resume.dto.CreateResumeRequest;
import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1")
public interface ResumeController {
    @PostMapping("/resume")
    Long createResume(@Valid @RequestBody CreateResumeRequest createResumeRequest);

    @PatchMapping("resume/{id}")
    Long updateResume(
            @Valid
            @PathVariable("id") Long resumeId,
            @RequestBody UpdateResumeRequest updateResumeRequest);
}

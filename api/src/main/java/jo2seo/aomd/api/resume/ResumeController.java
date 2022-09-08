package jo2seo.aomd.api.resume;

import jo2seo.aomd.api.resume.dto.PostResumeRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@RequestMapping("/api/v1")
public interface ResumeController {
    @PostMapping("/resume")
    Long createResume(@Valid @RequestBody PostResumeRequest postResumeRequest);
}

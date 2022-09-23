package jo2seo.aomd.service.resume;

import jo2seo.aomd.api.resume.dto.CreateResumeRequest;
import jo2seo.aomd.api.resume.dto.UpdateResumeRequest;

public interface ResumeService {

    Long createResume(CreateResumeRequest createResumeRequest);

    Long updateResume(Long resumeId, UpdateResumeRequest updateResumeRequest);
}

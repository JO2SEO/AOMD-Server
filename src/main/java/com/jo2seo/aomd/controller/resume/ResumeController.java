package com.jo2seo.aomd.controller.resume;

import com.jo2seo.aomd.dto.request.PostResumeRequest;
import com.jo2seo.aomd.service.resume.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ResumeController {
    private final ResumeService resumeService;

    @PostMapping("/resume")
    public Long save(
            @Valid @RequestBody PostResumeRequest postResumeRequest
    ) {
        return resumeService.save(postResumeRequest);
    }
}

package com.jo2seo.aomd.resume;

import com.jo2seo.aomd.resume.dto.PostResumeRequest;
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
        Long savedId = resumeService.save(postResumeRequest.getPortfolioId(), postResumeRequest.getQuestion(), postResumeRequest.getContent());
        return savedId;
    }
}

package com.jo2seo.aomd.service.resume;

import com.jo2seo.aomd.domain.Portfolio;
import com.jo2seo.aomd.domain.Resume;
import com.jo2seo.aomd.controller.resume.dto.PostResumeRequest;
import com.jo2seo.aomd.repository.resume.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public Long save(PostResumeRequest postResumeRequest) {
        Resume resume = new Resume(new Portfolio(), "question1", "content");
        resumeRepository.save(resume);
        return resume.getId();
    }
}

package com.jo2seo.aomd.resume;

import com.jo2seo.aomd.portfolio.Portfolio;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ResumeService {
    private final ResumeRepository resumeRepository;

    public Long save(Long portfolioId, String question, String content) {
        Resume resume = new Resume(new Portfolio(), "question1", "content");
        resumeRepository.save(resume);
        return resume.getId();
    }
}

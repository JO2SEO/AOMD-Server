package jo2seo.aomd.service.resume.mapper;

import jo2seo.aomd.api.resume.dto.ResumeDto;
import jo2seo.aomd.domain.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapperImpl implements ResumeMapper{
    @Override
    public ResumeDto entityToDTO(Resume entity) {
        if (entity == null) {
            return null;
        }

        ResumeDto resumeDto = new ResumeDto();

        resumeDto.setResumeId(entity.getId());
        resumeDto.setQuestion(entity.getQuestion());
        resumeDto.setContent(entity.getContent());
        
        resumeDto.setPortfolioId(0L);

        return resumeDto;
    }
}

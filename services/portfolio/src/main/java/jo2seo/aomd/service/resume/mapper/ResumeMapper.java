package jo2seo.aomd.service.resume.mapper;

import jo2seo.aomd.api.resume.dto.ResumeDto;
import jo2seo.aomd.domain.Resume;

public interface ResumeMapper {
    ResumeDto entityToDTO(Resume entity);
}

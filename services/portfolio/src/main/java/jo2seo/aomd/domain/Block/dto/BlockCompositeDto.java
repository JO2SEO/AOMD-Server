package jo2seo.aomd.domain.Block.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class BlockCompositeDto {
    private List<AwardDto> awardDtoList;
    private List<EducationDto> educationDtoList;
    private List<LicenseDto> licenseDtoList;
}

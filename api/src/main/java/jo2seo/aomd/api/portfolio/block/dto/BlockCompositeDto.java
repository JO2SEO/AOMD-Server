package jo2seo.aomd.api.portfolio.block.dto;

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

package jo2seo.aomd.domain.Block;

import jo2seo.aomd.api.portfolio.block.dto.BlockDto;

public interface Block<T extends BlockDto> {
     T toDto();
}

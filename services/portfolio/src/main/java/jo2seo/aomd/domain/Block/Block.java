package jo2seo.aomd.domain.Block;

import jo2seo.aomd.domain.Block.dto.BlockDto;

public interface Block<T extends BlockDto> {
     T toDto();
}

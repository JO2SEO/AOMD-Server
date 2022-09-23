package jo2seo.aomd.blockchain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import jo2seo.aomd.api.portfolio.block.dto.BlockDto;
import jo2seo.aomd.domain.Block.Block;
import jo2seo.aomd.utils.HttpApiController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BlockchainGateway {

    private static ObjectMapper mapper = new ObjectMapper();
    private final BlockchainUrl blockChainUrl;
    private final HttpApiController httpApiController;
    
    public <T extends BlockDto, B extends Block> List<T> getBlockList(String requestUrl, Class<B> c) {
        JavaType refType = mapper.getTypeFactory().
                constructCollectionType(List.class, c);
        try {
            List<B> blockList = mapper.readValue(getAndBody(requestUrl), refType);
            return blockList.stream()
                    .map(block -> (T) block.toDto())
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T extends BlockDto, B extends Block> Optional<T> getBlock(String requestUrl, Class<B> c) {
        try {
            return Optional.of((T) mapper.readValue(getAndBody(requestUrl), c).toDto());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private String getAndBody(String requestUrl) {
        return httpApiController.getRequest(requestUrl).getBody();
    }

    public String createBasicUrl(String detail) {
        return blockChainUrl.getDomain() + ":" + blockChainUrl.getPort() + detail;
    }

    public String createQueryUrl(
            String detail, 
            List<String> pathParams, 
            Map<String, String> conditions) {
        return blockChainUrl.getDomain() + ":" + blockChainUrl.getPort() + detail + "/" +
                String.join("/", pathParams) + "?" +
                conditions.keySet().stream()
                        .map(key -> key + "=" + conditions.get(key))
                        .collect(Collectors.joining("&"));
    }
}

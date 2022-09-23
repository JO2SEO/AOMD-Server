package jo2seo.aomd.blockchainapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jo2seo.aomd.blockchain.BlockchainGateway;
import jo2seo.aomd.blockchain.BlockchainUrl;
import jo2seo.aomd.domain.Block.Award;
import jo2seo.aomd.domain.Block.Education;
import jo2seo.aomd.domain.Block.License;
import jo2seo.aomd.utils.HttpApiController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@Disabled
@ActiveProfiles("test")
@SpringBootTest
public class BlockChainSDKConnectionTests {

    private ObjectMapper mapper = new ObjectMapper();
    private HttpApiController httpApiController;

    @Autowired
    private BlockchainGateway blockChainGateway;

    @Autowired
    private BlockchainUrl blockChainUrl;

    @Value("${blockchain.server.domain}")
    private String domain;

    @Value("${blockchain.server.port}")
    private String port;

    @Value("${blockchain.server.asset.award.all}")
    private String awardGetAll;

    @BeforeEach
    void createHttpApiController() {
        httpApiController = new HttpApiController();
    }
    
    @Test
    void getAllAwardTest() throws JsonProcessingException {
        
        ResponseEntity<String> request = 
                httpApiController.getRequest(domain + ":" + port + awardGetAll + "?memberId=1");

        List<Award> awardList = 
                mapper.readValue(request.getBody(), new TypeReference<>() {});

        System.out.println("SIZE : " + awardList.size());
        Assertions.assertEquals(5, awardList.size());
    }

    @Test
    void getBlockTest() throws JsonProcessingException {

        ResponseEntity<String> awardRequest =
                httpApiController.getRequest(domain + ":" + port + blockChainUrl.getAwardList() + "?memberId=user1@gmail.com");

        ResponseEntity<String> educationRequest =
                httpApiController.getRequest(domain + ":" + port + blockChainUrl.getEducationList() + "?memberId=user2@gmail.com");

        ResponseEntity<String> LicenseRequest =
                httpApiController.getRequest(domain + ":" + port + blockChainUrl.getLicenseList() + "?memberId=user3@gmail.com");

        
        List<Award> awardList =
                mapper.readValue(awardRequest.getBody(), new TypeReference<>() {});

        List<Education> educationList =
                mapper.readValue(educationRequest.getBody(), new TypeReference<>() {});
        
        List<License> licenseList =
                mapper.readValue(LicenseRequest.getBody(), new TypeReference<>() {});

        System.out.println("awardList = " + awardList);
        System.out.println("educationList = " + educationList);
        System.out.println("licenseList = " + licenseList);
        
        Assertions.assertEquals(5, awardList.size());
        Assertions.assertEquals(5, educationList.size());
        Assertions.assertEquals(5, licenseList.size());
    }
}

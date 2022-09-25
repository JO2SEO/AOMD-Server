package jo2seo.aomd.blockchain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BlockchainUrl {
    @Value("${blockchain.server.domain}")
    private String domain;
    @Value("${blockchain.server.port}")
    private String port;
    @Value("${blockchain.server.asset.award.all}")
    private String awardList;
    @Value("${blockchain.server.asset.license.all}")
    private String licenseList;
    @Value("${blockchain.server.asset.education.all}")
    private String educationList;
    
    @Value("${blockchain.server.asset.award.one}")
    private String award;
    @Value("${blockchain.server.asset.license.one}")
    private String license;
    @Value("${blockchain.server.asset.education.one}")
    private String education;

    @Value("${blockchain.server.asset.award.create}")
    private String awardCreateCreate;
    @Value("${blockchain.server.asset.license.create}")
    private String licenseCreate;
    @Value("${blockchain.server.asset.education.create}")
    private String educationCreate;

    @Value("${blockchain.server.asset.award.update}")
    private String awardUpdate;
    @Value("${blockchain.server.asset.license.update}")
    private String licenseUpdate;
    @Value("${blockchain.server.asset.education.update}")
    private String educationUpdate;

    @Value("${blockchain.server.asset.award.delete}")
    private String awardDelete;
    @Value("${blockchain.server.asset.license.delete}")
    private String licenseDelete;
    @Value("${blockchain.server.asset.education.delete}")
    private String educationDelete;
}

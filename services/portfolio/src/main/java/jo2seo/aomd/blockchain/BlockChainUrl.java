package jo2seo.aomd.blockchain;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BlockChainUrl {
    @Value("${blockchain.server.asset.award.get-all}")
    private String awardGetAll;
    @Value("${blockchain.server.asset.license.get-all}")
    private String licenseGetAll;
    @Value("${blockchain.server.asset.education.get-all}")
    private String educationGetAll;
    
    @Value("${blockchain.server.asset.award.get}")
    private String awardGet;
    @Value("${blockchain.server.asset.license.get}")
    private String licenseGet;
    @Value("${blockchain.server.asset.education.get}")
    private String educationGet;

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

package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.blockchain.BlockchainGateway;
import jo2seo.aomd.blockchain.BlockchainUrl;
import jo2seo.aomd.domain.Block.Award;
import jo2seo.aomd.domain.Block.Education;
import jo2seo.aomd.domain.Block.License;
import jo2seo.aomd.domain.Block.dto.AwardDto;
import jo2seo.aomd.domain.Block.dto.EducationDto;
import jo2seo.aomd.domain.Block.dto.LicenseDto;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.exception.Member.MemberNotFoundException;
import jo2seo.aomd.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class PortfolioBlockchainService {

    private final BlockchainGateway blockChainGateway;
    private final BlockchainUrl blockChainUrl;
    private final MemberRepository memberRepository;

    
    public List<AwardDto> findAwardListByMemberId(String memberEmail) {
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);
        
        return blockChainGateway.getBlockList(
                blockChainGateway.createQueryUrl(
                        blockChainUrl.getAwardList(),
                        new ArrayList<>(),
                        Map.of("memberId", member.getId().toString())),
                Award.class);
    }

    public List<EducationDto> findEducationListByMemberId(String memberEmail) {
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        return blockChainGateway.getBlockList(
                blockChainGateway.createQueryUrl(
                        blockChainUrl.getEducationList(),
                        new ArrayList<>(),
                        Map.of("memberId", member.getId().toString())),
                Education.class);
    }

    public List<LicenseDto> findLicenseListByMemberId(String memberEmail) {
        Member member = memberRepository.findMemberByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        return blockChainGateway.getBlockList(
                blockChainGateway.createQueryUrl(
                        blockChainUrl.getLicenseList(),
                        new ArrayList<>(),
                        Map.of("memberId", member.getId().toString())),
                License.class);
    }
}

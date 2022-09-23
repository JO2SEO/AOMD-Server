package jo2seo.aomd.service.portfolio;

import jo2seo.aomd.api.portfolio.block.dto.AwardDto;
import jo2seo.aomd.api.portfolio.block.dto.BlockCompositeDto;
import jo2seo.aomd.api.portfolio.block.dto.EducationDto;
import jo2seo.aomd.api.portfolio.block.dto.LicenseDto;
import jo2seo.aomd.blockchain.BlockchainGateway;
import jo2seo.aomd.blockchain.BlockchainUrl;
import jo2seo.aomd.domain.Block.Award;
import jo2seo.aomd.domain.Block.Block;
import jo2seo.aomd.domain.Block.Education;
import jo2seo.aomd.domain.Block.License;
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

    public BlockCompositeDto findBlockCompositeDto(String memberEmail) {
        Member member = memberRepository.findByEmail(memberEmail)
                .orElseThrow(MemberNotFoundException::new);

        List<AwardDto> awardDtoList = findAwardListByMember(member);
        List<EducationDto> educationDtoList = findEducationListByMember(member);
        List<LicenseDto> licenseDtoList = findLicenseListByMember(member);
        
        return new BlockCompositeDto(awardDtoList, educationDtoList, licenseDtoList);
    }

    private <B extends Block> List<?> findBlockList(Member member, String url, List<String> pathVariables, Class<B> blockClass) {
        return blockChainGateway.getBlockList(
                blockChainGateway.createQueryUrl(
                        url, 
                        pathVariables,
                        Map.of("memberId", member.getEmail())),
                blockClass
        );
    }
    
    public List<AwardDto> findAwardListByMember(Member member) {
        return (List<AwardDto>) findBlockList(
                member,
                blockChainUrl.getAwardList(),
                new ArrayList<>(),
                Award.class);
    }
    
    public List<EducationDto> findEducationListByMember(Member member) {
        return (List<EducationDto>) findBlockList(
                member,
                blockChainUrl.getEducationList(),
                new ArrayList<>(),
                Education.class);
    }

    public List<LicenseDto> findLicenseListByMember(Member member) {
        return (List<LicenseDto>) findBlockList(
                member,
                blockChainUrl.getLicenseList(),
                new ArrayList<>(),
                License.class);
    }
}

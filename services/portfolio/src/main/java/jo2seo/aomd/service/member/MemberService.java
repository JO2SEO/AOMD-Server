package jo2seo.aomd.service.member;

import jo2seo.aomd.api.member.dto.SignupRequest;
import jo2seo.aomd.domain.Member;

import java.util.List;

public interface MemberService {
    
    List<Member> findAllMembers();
    
    void signup(SignupRequest signupRequest);

    Member getMyMember();

    void updateProfileImg(String savedProfileImgUrl);
}
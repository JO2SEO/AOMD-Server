package jo2seo.aomd;

import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.UserRole;
import jo2seo.aomd.repository.portfolio.PortfolioRepository;
import jo2seo.aomd.repository.user.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
@Profile("!test & !prod")
public class PreProcessorDev {

    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void setUp() {
        initData();
    }
    
    private void initData() {
        String pw = passwordEncoder.encode("pwpw");
        Member member1 = new Member("user1@gmail.com", pw, "url", "member1", UserRole.USER);
        Member member2 = new Member("user2@gmail.com", pw, "url", "member2", UserRole.USER);
        Member member3 = new Member("user3@gmail.com", pw, "url", "member3", UserRole.USER);
        Member member4 = new Member("user4@gmail.com", pw, "url", "member3", UserRole.USER);
        Member member5 = new Member("user5@gmail.com", pw, "url", "member3", UserRole.USER);
        Member m1 = memberRepository.save(member1);
        Member m2 = memberRepository.save(member2);
        Member m3 = memberRepository.save(member3);
        Member m4 = memberRepository.save(member4);
        Member m5 = memberRepository.save(member5);

        Portfolio portfolio1 = new Portfolio(m1, "member1 pp");
        Portfolio portfolio2 = new Portfolio(m2, "member2 pp");
        Portfolio portfolio3 = new Portfolio(m3, "member3 pp");
        Portfolio portfolio4 = new Portfolio(m3, "member4 pp");
        Portfolio portfolio5 = new Portfolio(m3, "member5 pp");
        portfolioRepository.save(portfolio1);
        portfolioRepository.save(portfolio2);
        portfolioRepository.save(portfolio3);
        portfolioRepository.save(portfolio4);
        portfolioRepository.save(portfolio5);
    }
}

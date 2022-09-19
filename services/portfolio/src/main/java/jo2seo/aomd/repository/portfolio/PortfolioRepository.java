package jo2seo.aomd.repository.portfolio;

import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {

    @Override
    Portfolio save(Portfolio portfolio);
    
    @Override
    @Transactional(readOnly = true)
    Optional<Portfolio> findById(Long portfolioId);

    @Transactional(readOnly = true)
    Optional<Portfolio> findByShareUrl(String shareUrl);

    @Transactional(readOnly = true)
    Optional<Portfolio> findByMemberAndShareUrl(Member member, String shareUrl);

    @Transactional(readOnly = true)
    List<Portfolio> findAllByMember(Member member);
}

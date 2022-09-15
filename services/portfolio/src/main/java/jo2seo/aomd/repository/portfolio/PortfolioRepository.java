package jo2seo.aomd.repository.portfolio;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.Member;
import jo2seo.aomd.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {
    private final EntityManager em;

    public void save(Portfolio portfolio) {
        em.persist(portfolio);
    }


    public Optional<Portfolio> findOneById(Long portfolioId) {
        return Optional.of(em.createQuery("select p from Portfolio p where p.id = :id ", Portfolio.class)
                .setParameter("id", portfolioId)
                .getSingleResult()
        );
    }

    public Optional<Portfolio> findOneByUrl(String shareUrl) {
        return Optional.of(em.createQuery("select p from Portfolio p where p.shareUrl = :shareUrl", Portfolio.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult()
        );
    }

    public Optional<Portfolio> findOneByUserAndUrl(Member member, String shareUrl) {
        return Optional.of(em.createQuery("select p from Portfolio p where p.member = :member and p.shareUrl = :shareUrl", Portfolio.class)
                .setParameter("member", member)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult()
        );
    }

    public List<Portfolio> findAllByMember(Member member) {
        return em.createQuery("select p from Portfolio p where p.member = :member")
                .setParameter("member", member)
                .getResultList();
    }
}

package jo2seo.aomd.repository.portfolio;

import jo2seo.aomd.domain.Portfolio;
import jo2seo.aomd.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PortfolioRepository {
    private final EntityManager em;

    public void save(Portfolio portfolio) {
        em.persist(portfolio);
    }

    public boolean checkIsMine(String shareUrl, Member member) {
        Member owner = em.createQuery("select p.member from Portfolio p where p.shareUrl = :shareUrl", Member.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult();
        return owner.equals(member);
    }

    public boolean checkSharing(String shareUrl) {
        return em.createQuery("select p.sharing from Portfolio p where p.shareUrl = :shareUrl", Boolean.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult();
    }

    public Optional<String> findIdByUrl(String shareUrl) {
        return Optional.of(em.createQuery("select p.id from Portfolio p where p.shareUrl = :shareUrl", String.class)
                .setParameter("shareUrl", shareUrl)
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

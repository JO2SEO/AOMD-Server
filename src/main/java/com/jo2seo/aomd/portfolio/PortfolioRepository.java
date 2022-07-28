package com.jo2seo.aomd.portfolio;

import com.jo2seo.aomd.user.User;
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

    public boolean checkIsMine(String shareUrl, User user) {
        User owner = em.createQuery("select p.user from Portfolio p where p.shareUrl = :shareUrl", User.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult();
        return owner.equals(user);
    }

    public boolean checkShared(String shareUrl) {
        return em.createQuery("select p.isShared from Portfolio p where p.shareUrl = :shareUrl", Boolean.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult();
    }

    public Optional<Portfolio> findOneByUrl(String shareUrl) {
        return Optional.of(em.createQuery("select p from Portfolio p where p.shareUrl = :shareUrl", Portfolio.class)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult()
        );
    }

    public Optional<Portfolio> findOneByUserAndUrl(User user, String shareUrl) {
        return Optional.of(em.createQuery("select p from Portfolio p where p.user = :user and p.shareUrl = :shareUrl", Portfolio.class)
                .setParameter("user", user)
                .setParameter("shareUrl", shareUrl)
                .getSingleResult()
        );
    }

    public List<Portfolio> findAllByUser(User user) {
        return em.createQuery("select p from Portfolio p where p.user = :user")
                .setParameter("user", user)
                .getResultList();
    }
}

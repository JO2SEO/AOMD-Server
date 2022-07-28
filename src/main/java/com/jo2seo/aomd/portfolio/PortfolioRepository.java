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

    public Optional<Portfolio> find(Long id) {
        return Optional.of(em.find(Portfolio.class, id));
    }

    public List<Portfolio> findAll() {
        return em.createQuery("select p from Portfolio p").getResultList();
    }

    public List<Portfolio> findAllByUser(User user) {
        return em.createQuery("select p from Portfolio p where p.user = :user")
                .setParameter("user", user)
                .getResultList();
    }
}

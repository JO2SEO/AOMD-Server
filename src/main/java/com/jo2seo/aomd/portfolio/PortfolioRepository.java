package com.jo2seo.aomd.portfolio;

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
}

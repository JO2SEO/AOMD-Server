package jo2seo.aomd.repository.resume;

import jo2seo.aomd.domain.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ResumeRepository {
    private final EntityManager em;

    public void save(Resume resume) {
        em.persist(resume);
    }

    public Optional<Resume> find(Long id) {
        return Optional.of(em.find(Resume.class, id));
    }

    public List findAll() {
        return em.createQuery("select r from Resume r").getResultList();
    }
}

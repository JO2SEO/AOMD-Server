package jo2seo.aomd.repository.resume;

import jo2seo.aomd.domain.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    @Override
    Resume save(Resume resume);

    @Override
    @Transactional(readOnly = true)
    Optional<Resume> findById(Long id);

    @Override
    @Transactional(readOnly = true)
    List<Resume> findAll();
}

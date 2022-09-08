package jo2seo.aomd.repository.user;

import jo2seo.aomd.domain.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberRepository extends CrudRepository<Member, String> {

    @Transactional(readOnly = true)
    Optional<Member> findMemberByEmail(String email);

    @Transactional(readOnly = true)
    List<Member> findAll();
    
}

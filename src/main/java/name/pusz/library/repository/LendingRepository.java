package name.pusz.library.repository;

import name.pusz.library.domain.Copy;
import name.pusz.library.domain.Lending;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LendingRepository extends CrudRepository<Lending, Long> {

    @Override
    Lending save(Lending lending);

    @Query
    List<Lending> lentByMemberNotReturned(@Param("MEMBER_ID") Long memberId);

    List<Lending> findByCopyAndReturnDateIsNull(Copy copy);
}

package name.pusz.library.mapper;

import name.pusz.library.domain.Lending;
import name.pusz.library.domain.dto.outgoing.LendingDtoOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LendingMapper {

    @Autowired
    private CopyMapper copyMapper;

    @Autowired
    private MemberMapper memberMapper;

    public LendingDtoOut mapToLendingDtoOut(Lending lending) {
        return new LendingDtoOut(
                lending.getId(),
                copyMapper.mapToCopyDtoOut(lending.getCopy()),
                memberMapper.mapToMemberDtoOut(lending.getLibraryMember()),
                Date.valueOf(lending.getLendingDate()),
                Optional.ofNullable(lending.getReturnDate())
                        .map(Date::valueOf)
                        .orElse(null));
    }

    public List<LendingDtoOut> mapToLendingDtoOutList(List<Lending> lendingBookList) {
        return lendingBookList.stream()
                .map(lending -> mapToLendingDtoOut(lending))
                .collect(Collectors.toList());
    }
}

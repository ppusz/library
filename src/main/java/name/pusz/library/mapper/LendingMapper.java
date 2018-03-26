package name.pusz.library.mapper;

import name.pusz.library.domain.Lending;
import name.pusz.library.domain.dto.outgoing.LendingDtoOut;
import name.pusz.library.utils.LocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class LendingMapper {

    @Autowired
    private CopyMapper copyMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private LocalDateConverter dateConverter;

    public LendingDtoOut mapToLendingDtoOut(Lending lending) {
        Date returnDate = Optional.ofNullable(lending.getReturnDate())
                .map(d -> dateConverter.convertToDatabaseColumn(d))
                .orElse(null);

        return new LendingDtoOut(
                lending.getId(),
                copyMapper.mapToCopyDtoOut(lending.getCopy()),
                memberMapper.mapToMemberDtoOut(lending.getLibraryMember()),
                dateConverter.convertToDatabaseColumn(lending.getLendingDate()),
                returnDate);
    }

    public List<LendingDtoOut> mapToLendingDtoOutList(List<Lending> lendingBookList) {
        return lendingBookList.stream()
                .map(lending -> mapToLendingDtoOut(lending))
                .collect(Collectors.toList());
    }
}

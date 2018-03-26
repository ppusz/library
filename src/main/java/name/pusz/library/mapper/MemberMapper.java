package name.pusz.library.mapper;

import name.pusz.library.domain.LibraryMember;
import name.pusz.library.domain.dto.incoming.MemberDtoIn;
import name.pusz.library.domain.dto.outgoing.MemberDtoOut;
import name.pusz.library.utils.LocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    @Autowired
    private LocalDateConverter dateConverter;

    public MemberDtoOut mapToMemberDtoOut(LibraryMember libraryMember) {
        return new MemberDtoOut(
                libraryMember.getId(),
                libraryMember.getFirstName(),
                libraryMember.getLastName(),
                Date.valueOf(libraryMember.getMemberSince())
        );
    }

    public List<MemberDtoOut> mapToMemberDtoOutList(List<LibraryMember> libraryMemberList) {
        return libraryMemberList.stream()
                .map(member -> mapToMemberDtoOut(member))
                .collect(Collectors.toList());
    }

    public LibraryMember mapToMember(MemberDtoIn memberDtoIn) {
        return new LibraryMember(
                memberDtoIn.getId(),
                memberDtoIn.getFirstName(),
                memberDtoIn.getLastName(),
                dateConverter.convertToEntityAttribute(memberDtoIn.getMemberSince()),
                null);
    }
}

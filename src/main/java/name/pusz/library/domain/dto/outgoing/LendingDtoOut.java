package name.pusz.library.domain.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class LendingDtoOut {

    private Long id;
    private CopyDtoOut copyDtoOut;
    private MemberDtoOut memberDto;
    private Date lendingDate;
    private Date returnDate;
}

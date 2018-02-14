package name.pusz.library.domain.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LendingDtoIn {

    private Long copyId;
    private Long memberId;
}

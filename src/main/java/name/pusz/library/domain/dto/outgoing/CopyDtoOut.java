package name.pusz.library.domain.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CopyDtoOut {

    private Long id;
    private BookDtoOut bookDto;
    private String status;
}

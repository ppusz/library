package name.pusz.library.domain.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CopyDtoIn {

    private Long id;
    private Long bookId;
    private String status;
}

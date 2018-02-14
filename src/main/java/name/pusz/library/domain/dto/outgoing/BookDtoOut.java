package name.pusz.library.domain.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDtoOut {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}

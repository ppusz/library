package name.pusz.library.domain.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookDtoIn {

    private Long id;
    private String title;
    private String author;
    private int publicationYear;
}

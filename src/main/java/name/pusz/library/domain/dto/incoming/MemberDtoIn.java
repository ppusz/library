package name.pusz.library.domain.dto.incoming;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
@AllArgsConstructor
public class MemberDtoIn {

    private long id;
    private String firstName;
    private String lastName;
    private Date memberSince;
}

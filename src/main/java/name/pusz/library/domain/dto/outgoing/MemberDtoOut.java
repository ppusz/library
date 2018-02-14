package name.pusz.library.domain.dto.outgoing;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Date;


@Getter
@AllArgsConstructor
public class MemberDtoOut {

    private long id;
    private String firstName;
    private String lastName;
    private Date memberSince;
}

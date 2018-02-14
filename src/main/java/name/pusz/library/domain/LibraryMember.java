package name.pusz.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import name.pusz.library.utils.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "members")
public class LibraryMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Column(name = "firstname")
    private String firstName;

    @NotNull
    @Column(name = "lastname")
    private String lastName;

    @NotNull
    @Column(name = "member_since")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate memberSince;

    @OneToMany(
            targetEntity = Lending.class,
            mappedBy = "libraryMember",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Lending> lendings;

    public LibraryMember(String firstName, String lastName, LocalDate memberSince) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.memberSince = memberSince;
    }
}

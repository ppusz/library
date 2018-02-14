package name.pusz.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import name.pusz.library.utils.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Lending.lentByMemberNotReturned",
                query = "FROM Lending " +
                        "WHERE libraryMember.id = :MEMBER_ID AND returnDate IS NULL"
        )
})
@Entity
@Table(name = "lendings")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "member_id")
    private LibraryMember libraryMember;

    @NotNull
    @Column(name = "lending_date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate lendingDate;

    @Column(name = "return_date")
    @Convert(converter = LocalDateConverter.class)
    private LocalDate returnDate;

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Lending(Copy copy, LibraryMember libraryMember, LocalDate lendingDate) {
        this.copy = copy;
        this.libraryMember = libraryMember;
        this.lendingDate = lendingDate;
    }
}

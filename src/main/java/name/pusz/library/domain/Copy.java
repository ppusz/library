package name.pusz.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(
                name = "Copy.retrieveAvailableQuantity",
                query = "SELECT COUNT(*) " +
                        "FROM Copy " +
                        "WHERE book.id = :BOOK_ID " +
                        "AND id not in " +
                        "(SELECT copy FROM Lending WHERE returnDate is null)"
        ),
        @NamedQuery(
                name = "Copy.retrieveAvailableCopies",
                query = "FROM Copy " +
                        "WHERE book.id = :BOOK_ID " +
                        "AND id not in " +
                        "(SELECT copy FROM Lending WHERE returnDate is null)"
        )
})
@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @NotNull
    @Column(name = "status")
    private String status;

    @OneToMany(
            targetEntity = Lending.class,
            mappedBy = "copy",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Lending> lendings;
}

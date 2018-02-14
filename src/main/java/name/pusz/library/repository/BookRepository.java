package name.pusz.library.repository;

import name.pusz.library.domain.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    @Override
    Book save(Book book);

    Optional<Book> findById(Long id);

    List<Book> findByAuthor(String author);

    List<Book> findByTitle(String title);

}

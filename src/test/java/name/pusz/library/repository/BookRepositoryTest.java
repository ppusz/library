package name.pusz.library.repository;

import name.pusz.library.domain.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository repository;

    @Test
    public void shouldSave() {
        // Given
        Book book = new Book(null, "Test title", "Test Author", 1999, new ArrayList<>());

        // When
        Book savedBook = repository.save(book);
        long id = savedBook.getId();

        // Then
        assertNotEquals(0L, id);
    }

    @Test
    public void shouldFindById() {
        // Given
        Book book = new Book(null, "Test title", "Test Author", 1999, new ArrayList<>());
        long bookId = repository.save(book).getId();

        // When
        Optional<Book> retrievedBook = repository.findById(bookId);

        // Then
        assertTrue(retrievedBook.isPresent());
    }

    @Test
    public void shouldFindByAuthor() {
        // Given
        Book book1 = new Book(null, "Test title", "Author 1", 1999, new ArrayList<>());
        Book book2 = new Book(null, "Test title", "Author 2", 2000, new ArrayList<>());
        Book book3 = new Book(null, "Test title", "Author 2", 2001, new ArrayList<>());

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);

        // When
        List<Book> retrievedBooks = repository.findByAuthor("Author 2");

        // Then
        assertEquals(2, retrievedBooks.size());
    }

    @Test
    public void shouldFindByTitle() {
        // Given
        Book book1 = new Book(null, "Title 1", "Author 1", 1999, new ArrayList<>());
        Book book2 = new Book(null, "Title 1", "Author 2", 2000, new ArrayList<>());
        Book book3 = new Book(null, "Title 2", "Author 3", 2001, new ArrayList<>());

        repository.save(book1);
        repository.save(book2);
        repository.save(book3);

        // When
        List<Book> retrievedBooks = repository.findByTitle("Title 1");

        // Then
        assertEquals(2, retrievedBooks.size());
    }
}
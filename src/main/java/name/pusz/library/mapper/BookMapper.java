package name.pusz.library.mapper;

import name.pusz.library.domain.Book;
import name.pusz.library.domain.dto.incoming.BookDtoIn;
import name.pusz.library.domain.dto.outgoing.BookDtoOut;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public BookDtoOut mapToBookDtoOut(final Book book) {
        return new BookDtoOut(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getPublicationYear());
    }

    public List<BookDtoOut> mapToBookDtoOutList(final List<Book> bookList) {
        return bookList.stream()
                .map(book -> mapToBookDtoOut(book))
                .collect(Collectors.toList());
    }

    public Book mapToBook(final BookDtoIn bookDtoIn) {
        return new Book(
                bookDtoIn.getId(),
                bookDtoIn.getTitle(),
                bookDtoIn.getAuthor(),
                bookDtoIn.getPublicationYear(),
                null);
    }
}

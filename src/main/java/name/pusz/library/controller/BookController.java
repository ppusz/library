package name.pusz.library.controller;

import name.pusz.library.controller.exception.BookNotFoundException;
import name.pusz.library.controller.exception.CopyNotFoundException;
import name.pusz.library.domain.dto.incoming.BookDtoIn;
import name.pusz.library.domain.dto.incoming.CopyDtoIn;
import name.pusz.library.domain.dto.outgoing.BookDtoOut;
import name.pusz.library.domain.dto.outgoing.CopyDtoOut;
import name.pusz.library.mapper.BookMapper;
import name.pusz.library.mapper.CopyMapper;
import name.pusz.library.repository.BookRepository;
import name.pusz.library.repository.CopyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/book")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    CopyRepository copyRepository;
    @Autowired
    CopyMapper copyMapper;

    @GetMapping(value = "getBookById")
    public BookDtoOut getBook(@RequestParam Long bookId) throws BookNotFoundException {
        return bookMapper.mapToBookDtoOut(bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new));
    }

    @GetMapping(value = "getByAuthor")
    public List<BookDtoOut> getBooksByAuthor(@RequestParam String author) {
        return bookMapper.mapToBookDtoOutList(bookRepository.findByAuthor(author));
    }

    @GetMapping(value = "getByTitle")
    public List<BookDtoOut> getBooksByTitle(@RequestParam String title) {
        return bookMapper.mapToBookDtoOutList(bookRepository.findByTitle(title));
    }

    @PostMapping(value = "createBook", consumes = APPLICATION_JSON_VALUE)
    public BookDtoOut createBook(@RequestBody BookDtoIn bookDtoIn) {
        return bookMapper.mapToBookDtoOut(bookRepository.save(bookMapper.mapToBook(bookDtoIn)));
    }

    @PutMapping(value = "updateBook", consumes = APPLICATION_JSON_VALUE)
    public BookDtoOut updateBook(@RequestBody BookDtoIn bookDtoIn) {
        return bookMapper.mapToBookDtoOut(bookRepository.save(bookMapper.mapToBook(bookDtoIn)));
    }

    @GetMapping(value = "getCopyById")
    public CopyDtoOut getCopy(@RequestParam Long copyId) throws CopyNotFoundException {
        return copyMapper.mapToCopyDtoOut(copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new));
    }

    @GetMapping(value = "getAvailableQuantity")
    public int getAvailableQuantity(@RequestParam Long bookId) {
        return copyRepository.retrieveAvailableQuantity(bookId);
    }

    @GetMapping(value = "getAvailableCopies")
    public List<CopyDtoOut> getAvailableCopies(@RequestParam Long bookId) {
        return copyMapper.mapToCopyDtoOutList(copyRepository.retrieveAvailableCopies(bookId));
    }

    @PostMapping(value = "createCopy", consumes = APPLICATION_JSON_VALUE)
    public CopyDtoOut createCopy(@RequestBody CopyDtoIn copyDtoIn) throws BookNotFoundException {
        return copyMapper.mapToCopyDtoOut(copyRepository.save(copyMapper.mapToCopy(copyDtoIn)));
    }

    @PutMapping(value = "updateCopy", consumes = APPLICATION_JSON_VALUE)
    public CopyDtoOut updateCopy(@RequestBody CopyDtoIn copyDtoIn) throws BookNotFoundException {
        return copyMapper.mapToCopyDtoOut(copyRepository.save(copyMapper.mapToCopy(copyDtoIn)));
    }
}

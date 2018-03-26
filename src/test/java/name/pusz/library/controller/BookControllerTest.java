package name.pusz.library.controller;

import com.google.gson.Gson;
import name.pusz.library.domain.Book;
import name.pusz.library.domain.Copy;
import name.pusz.library.domain.dto.incoming.BookDtoIn;
import name.pusz.library.domain.dto.incoming.CopyDtoIn;
import name.pusz.library.domain.dto.outgoing.BookDtoOut;
import name.pusz.library.domain.dto.outgoing.CopyDtoOut;
import name.pusz.library.mapper.BookMapper;
import name.pusz.library.mapper.CopyMapper;
import name.pusz.library.repository.BookRepository;
import name.pusz.library.repository.CopyRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private CopyRepository copyRepository;
    @MockBean
    private CopyMapper copyMapper;

    @Test
    public void shouldGetBook() throws Exception {
        // Given
        BookDtoOut bookDtoOut = new BookDtoOut(6453L, "Test title", "Test author", 1234);
        Optional<Book> book = Optional.of(new Book(6453L, "Test title", "Test author", 1234, new ArrayList<>()));

        when(bookMapper.mapToBookDtoOut(book.get())).thenReturn(bookDtoOut);
        when(bookRepository.findById(anyLong())).thenReturn(book);

        // When & Then
        mockMvc.perform(get("/v1/book/getBookById")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "6453"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(6453)))
                .andExpect(jsonPath("$.title", is("Test title")))
                .andExpect(jsonPath("$.author", is("Test author")))
                .andExpect(jsonPath("$.publicationYear", is(1234)));
    }

    @Test
    public void shouldGetBookByAuthor() throws Exception {
        // Given
        List<BookDtoOut> bookList = new ArrayList<>();
        bookList.add(new BookDtoOut(12L, "Title 1", "Author", 1234));
        bookList.add(new BookDtoOut(13L, "Title 2", "Author", 1235));

        when(bookMapper.mapToBookDtoOutList(anyList())).thenReturn(bookList);
        when(bookRepository.findByAuthor(anyString())).thenReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/v1/book/getByAuthor")
                .contentType(MediaType.APPLICATION_JSON)
                .param("author", "Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(12)))
                .andExpect(jsonPath("$[0].title", is("Title 1")))
                .andExpect(jsonPath("$[0].author", is("Author")))
                .andExpect(jsonPath("$[0].publicationYear", is(1234)))
                .andExpect(jsonPath("$[1].id", is(13)))
                .andExpect(jsonPath("$[1].title", is("Title 2")))
                .andExpect(jsonPath("$[1].author", is("Author")))
                .andExpect(jsonPath("$[1].publicationYear", is(1235)));
    }

    @Test
    public void shouldGetBookByTitle() throws Exception {
        // Given
        List<BookDtoOut> bookList = new ArrayList<>();
        bookList.add(new BookDtoOut(121L, "Title", "Author 1", 1234));
        bookList.add(new BookDtoOut(131L, "Title", "Author 2", 1235));

        when(bookMapper.mapToBookDtoOutList(anyList())).thenReturn(bookList);
        when(bookRepository.findByAuthor(anyString())).thenReturn(new ArrayList<>());

        // When & Then
        mockMvc.perform(get("/v1/book/getByAuthor")
                .contentType(MediaType.APPLICATION_JSON)
                .param("author", "Author"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(121)))
                .andExpect(jsonPath("$[0].title", is("Title")))
                .andExpect(jsonPath("$[0].author", is("Author 1")))
                .andExpect(jsonPath("$[0].publicationYear", is(1234)))
                .andExpect(jsonPath("$[1].id", is(131)))
                .andExpect(jsonPath("$[1].title", is("Title")))
                .andExpect(jsonPath("$[1].author", is("Author 2")))
                .andExpect(jsonPath("$[1].publicationYear", is(1235)));
    }

    @Test
    public void shouldCreateBook() throws Exception {
        // Given
        BookDtoIn bookDtoIn = new BookDtoIn(null, "Title", "Author", 3254);
        Book book = new Book(65L, "Title", "Author", 3254, new ArrayList<>());

        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookDtoIn);

        // When & Then
        mockMvc.perform(post("/v1/book/createBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdateBook() throws Exception {
        // Given
        BookDtoIn bookDtoIn = new BookDtoIn(564L, "Updated Title", "Updated Author", 4564);
        BookDtoOut bookDtoOut = new BookDtoOut(564L, "Updated Title", "Updated Author", 4564);
        Book book = new Book(564L, "Updated Title", "Updated Author", 4564, null);

        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.mapToBook(any(BookDtoIn.class))).thenReturn(book);
        when(bookMapper.mapToBookDtoOut(book)).thenReturn(bookDtoOut);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(bookDtoIn);

        // When & Then
        mockMvc.perform(put("/v1/book/updateBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(564)))
                .andExpect(jsonPath("$.title", is("Updated Title")))
                .andExpect(jsonPath("$.author", is("Updated Author")))
                .andExpect(jsonPath("$.publicationYear", is(4564)));
    }

    @Test
    public void shouldGetCopyById() throws Exception {
        // Given
        BookDtoOut bookDtoOut = new BookDtoOut(6453L, "Test title", "Test author", 1234);
        Book book = (new Book(6453L, "Test title", "Test author", 1234, new ArrayList<>()));
        CopyDtoOut copyDtoOut = new CopyDtoOut(564L, bookDtoOut, "status");
        Copy copy = new Copy(564L, book, "status", null);

        when(copyRepository.findById(anyLong())).thenReturn(Optional.of(copy));
        when(copyMapper.mapToCopyDtoOut(copy)).thenReturn(copyDtoOut);

        // When & Then
        mockMvc.perform(get("/v1/book/getCopyById")
                .contentType(MediaType.APPLICATION_JSON)
                .param("copyId", "564"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(564)))
                .andExpect(jsonPath("$.status", is("status")))
                .andExpect(jsonPath("$.bookDto.id", is(6453)))
                .andExpect(jsonPath("$.bookDto.title", is("Test title")))
                .andExpect(jsonPath("$.bookDto.author", is("Test author")))
                .andExpect(jsonPath("$.bookDto.publicationYear", is(1234)));

    }


    @Test
    public void shouldGetAvailableQuantity() throws Exception {
        // Given
        when(copyRepository.retrieveAvailableQuantity(123L)).thenReturn(12);

        // When & Then
        mockMvc.perform(get("/v1/book/getAvailableQuantity")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(12)));
    }

    @Test
    public void shouldGetAvailableCopies() throws Exception {
        // Given
        List<Copy> copies = new ArrayList<>();
        Book book = new Book(48L,"Title", "Author", 2018, new ArrayList<Copy>());
        copies.add(new Copy(564L, book, "new", null));
        copies.get(0).getBook().getCopies().add(copies.get(0));
        List<CopyDtoOut> copiesDtoOut = new ArrayList<>();
        BookDtoOut bookDtoOut = new BookDtoOut(48L,"Title", "Author", 2018);
        copiesDtoOut.add(new CopyDtoOut(564L, bookDtoOut, "new"));

        when(copyRepository.retrieveAvailableCopies(48L)).thenReturn(copies);
        when(copyMapper.mapToCopyDtoOutList(copies)).thenReturn(copiesDtoOut);

        // When & Then
        mockMvc.perform(get("/v1/book/getAvailableCopies")
                .contentType(MediaType.APPLICATION_JSON)
                .param("bookId", "48"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(564)))
                .andExpect(jsonPath("$[0].status", is("new")))
                .andExpect(jsonPath("$[0].bookDto.id", is(48)))
                .andExpect(jsonPath("$[0].bookDto.title", is("Title")))
                .andExpect(jsonPath("$[0].bookDto.author", is("Author")))
                .andExpect(jsonPath("$[0].bookDto.publicationYear", is(2018)));
    }

    @Test
    public void shouldCreateCopy() throws Exception {
        // Given
        CopyDtoOut copyDto = new CopyDtoOut(
                1L,
                new BookDtoOut(2L,"Title", "Author", 2017),
                "old");

        Copy copy = new Copy(
                1L,
                new Book(2L,"Title", "Author", 2017, null),
                "old",
                null);

        when(copyMapper.mapToCopy(any(CopyDtoIn.class))).thenReturn(copy);
        when(copyRepository.save(copy)).thenReturn(copy);
        when(copyMapper.mapToCopyDtoOut(copy)).thenReturn(copyDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyDto);

        // When  & Then
        mockMvc.perform(post("/v1/book/createCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.status", is("old")))
                .andExpect(jsonPath("$.bookDto.id", is(2)))
                .andExpect(jsonPath("$.bookDto.title", is("Title")))
                .andExpect(jsonPath("$.bookDto.author", is("Author")))
                .andExpect(jsonPath("$.bookDto.publicationYear", is(2017)));
    }

    @Test
    public void shouldUpdateCopy() throws Exception {
        // Given
        CopyDtoOut copyDto = new CopyDtoOut(
                11L,
                new BookDtoOut(22L,"Updated title", "Updated author", 2011),
                "trash");

        Copy copy = new Copy(
                11L,
                new Book(22L,"Updated title", "Updated author", 2011, null),
                "trash",
                null);

        when(copyMapper.mapToCopy(any(CopyDtoIn.class))).thenReturn(copy);
        when(copyRepository.save(copy)).thenReturn(copy);
        when(copyMapper.mapToCopyDtoOut(copy)).thenReturn(copyDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(copyDto);

        // When  & Then
        mockMvc.perform(put("/v1/book/updateCopy")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(11)))
                .andExpect(jsonPath("$.status", is("trash")))
                .andExpect(jsonPath("$.bookDto.id", is(22)))
                .andExpect(jsonPath("$.bookDto.title", is("Updated title")))
                .andExpect(jsonPath("$.bookDto.author", is("Updated author")))
                .andExpect(jsonPath("$.bookDto.publicationYear", is(2011)));
    }
}

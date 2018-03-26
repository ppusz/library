package name.pusz.library.service;

import name.pusz.library.controller.exception.CanNotReturnException;
import name.pusz.library.controller.exception.CopyNotFoundException;
import name.pusz.library.controller.exception.MemberNotFoundException;
import name.pusz.library.domain.Book;
import name.pusz.library.domain.Copy;
import name.pusz.library.domain.Lending;
import name.pusz.library.domain.LibraryMember;
import name.pusz.library.repository.CopyRepository;
import name.pusz.library.repository.LendingRepository;
import name.pusz.library.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LendingServiceTest {

    @InjectMocks
    private LendingService lendingService;

    @Mock
    private CopyRepository copyRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private LendingRepository lendingRepository;

    @Test
    public void shouldLend() throws MemberNotFoundException, CopyNotFoundException {
        // Given
        LibraryMember member = new LibraryMember(234L, "John", "Smith", LocalDate.now(), new ArrayList<>());
        Book book = new Book(654L, "Test title", "Test Author", 1999, new ArrayList<>());
        Copy copy = new Copy(8648L, book, "status", new ArrayList<>());
        book.getCopies().add(copy);

        when(memberRepository.findById(234L)).thenReturn(Optional.of(member));
        when(copyRepository.findById(8648L)).thenReturn(Optional.of(copy));
        when(lendingRepository.save(any(Lending.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Lending retrievedLending = lendingService.lend(copy.getId(), member.getId());

        // Then
        assertEquals(copy, retrievedLending.getCopy());
        assertEquals(member, retrievedLending.getLibraryMember());
    }

    @Test
    public void shouldReturn() throws CopyNotFoundException, CanNotReturnException {
        // Given
        LibraryMember member = new LibraryMember(234L, "John", "Smith", LocalDate.now(), new ArrayList<>());
        Book book = new Book(654L, "Test title", "Test Author", 1999, new ArrayList<>());
        Copy copy = new Copy(8648L, book, "status", new ArrayList<>());
        book.getCopies().add(copy);
        List<Lending> lendings = new ArrayList<>();
        lendings.add(new Lending(582L, copy, member, LocalDate.now(), null));

        when(copyRepository.findById(8648L)).thenReturn(Optional.of(copy));
        when(lendingRepository.findByCopyAndReturnDateIsNull(copy)).thenReturn(lendings);
        when(lendingRepository.save(any(Lending.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Lending retrievedLending = lendingService.returnBook(copy.getId());

        // Then
        assertNotNull(retrievedLending.getReturnDate());
    }
}

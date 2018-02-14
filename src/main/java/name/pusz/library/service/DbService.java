package name.pusz.library.service;

import name.pusz.library.controller.exception.BookNotFoundException;
import name.pusz.library.domain.Book;
import name.pusz.library.domain.LibraryMember;
import name.pusz.library.repository.BookRepository;
import name.pusz.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DbService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public Book findBookWithId(Long bookId) throws BookNotFoundException {
        return bookRepository.findById(bookId).orElseThrow(BookNotFoundException::new);
    }

    public LibraryMember createMember(String firstName, String lastName) {
        return memberRepository.save(new LibraryMember(firstName, lastName, LocalDate.now()));
    }
}

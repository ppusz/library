package name.pusz.library.service;

import name.pusz.library.controller.exception.CanNotReturnException;
import name.pusz.library.controller.exception.CopyNotFoundException;
import name.pusz.library.controller.exception.MemberNotFoundException;
import name.pusz.library.domain.Copy;
import name.pusz.library.domain.Lending;
import name.pusz.library.domain.LibraryMember;
import name.pusz.library.repository.CopyRepository;
import name.pusz.library.repository.LendingRepository;
import name.pusz.library.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LendingService {

    @Autowired
    private CopyRepository copyRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LendingRepository lendingRepository;

    public Lending lend(Long copyId, Long memberId) throws MemberNotFoundException, CopyNotFoundException {
        LibraryMember libraryMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Copy copy = copyRepository.findById(copyId).orElseThrow(CopyNotFoundException::new);
        Lending lending = new Lending(
                copy,
                libraryMember,
                LocalDate.now());
        return lendingRepository.save(lending);
    }

    public Lending returnBook(Long copyID) throws CopyNotFoundException, CanNotReturnException {
        Copy copyToBeReturned = copyRepository.findById(copyID).orElseThrow(CopyNotFoundException::new);
        List<Lending> lendingToBeReturned = lendingRepository.findByCopyAndReturnDateIsNull(copyToBeReturned);
        if (lendingToBeReturned.size() == 0) {
            throw new CanNotReturnException();
        }
        for (Lending lending : lendingToBeReturned) {
            lending.setReturnDate(LocalDate.now());
            lending = lendingRepository.save(lending);
        }
        return lendingToBeReturned.get(lendingToBeReturned.size() - 1);
    }
}

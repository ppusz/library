package name.pusz.library.controller;

import name.pusz.library.controller.exception.CanNotReturnException;
import name.pusz.library.controller.exception.CopyNotFoundException;
import name.pusz.library.controller.exception.MemberNotFoundException;
import name.pusz.library.domain.dto.incoming.LendingDtoIn;
import name.pusz.library.domain.dto.outgoing.LendingDtoOut;
import name.pusz.library.mapper.LendingMapper;
import name.pusz.library.repository.LendingRepository;
import name.pusz.library.service.LendingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/lending")
@CrossOrigin(origins = "*")
public class LendingController {

    @Autowired
    private LendingRepository lendingRepository;

    @Autowired
    private LendingMapper lendingMapper;

    @Autowired
    private LendingService lendingService;

    @PostMapping(value = "lend", consumes = APPLICATION_JSON_VALUE)
    public LendingDtoOut lend(@RequestBody LendingDtoIn lendingDtoIn) throws CopyNotFoundException, MemberNotFoundException {
        return lendingMapper.mapToLendingDtoOut(lendingService.lend(lendingDtoIn.getCopyId(), lendingDtoIn.getMemberId()));
    }

    @GetMapping(value = "getLentByMember")
    public List<LendingDtoOut> getLendingsByMember(@RequestParam Long memberId) {
        return lendingMapper.mapToLendingDtoOutList(lendingRepository.lentByMemberNotReturned(memberId));
    }

    @PutMapping(value = "return")
    public LendingDtoOut returnBook(@RequestParam Long copyId) throws CopyNotFoundException, CanNotReturnException {
        return lendingMapper.mapToLendingDtoOut(lendingService.returnBook(copyId));
    }
}
